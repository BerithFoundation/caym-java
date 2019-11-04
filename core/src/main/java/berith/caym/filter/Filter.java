/*
 * Modifications Copyright 2019 The Berith foundation authors.
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package berith.caym.filter;

import berith.caym.Caym;
import berith.caym.protocol.response.BerithFilter;
import berith.caym.protocol.response.BerithLog;
import berith.caym.protocol.response.BerithLog.LogResult;
import berith.caym.protocol.response.BerithUninstallFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.Response.Error;
import org.web3j.protocol.core.RpcErrors;

/**
 * Class for creating managed filter requests with callbacks.
 */
public abstract class Filter<T> {

    private static final Logger log = LoggerFactory.getLogger(Filter.class);

    // final Web3j web3j;
    final Caym caym;
    final Callback<T> callback;

    private volatile BigInteger filterId;
    private ScheduledFuture<?> schedule;
    private ScheduledExecutorService scheduledExecutorService;
    private long pollingInterval;

    public Filter(Caym caym, Callback<T> callback) {
        this.caym = caym;
        this.callback = callback;
    }

    public void run(ScheduledExecutorService scheduledExecutorService, long pollingInterval) {
        try {
            BerithFilter berithFilter = sendRequest();
            if (berithFilter.hasError()) {
                throwException(berithFilter.getError());
            }

            filterId = berithFilter.getFilterId();
            this.scheduledExecutorService = scheduledExecutorService;
            this.pollingInterval = pollingInterval;
            // this runs in the caller thread as if any exceptions are encountered, we shouldn't
            // proceed with creating the scheduled task below
            getInitialFilterLogs();

            /*
            We want the filter to be resilient against client issues. On numerous occasions
            users have reported socket timeout exceptions when connected over HTTP to Geth and
            Parity clients. For examples, refer to
            https://github.com/web3j/web3j/issues/144 and
            https://github.com/ethereum/go-ethereum/issues/15243.

            Hence we consume errors and log them as errors, allowing our polling for changes to
            resume. The downside of this approach is that users will not be notified of
            downstream connection issues. But given the intermittent nature of the connection
            issues, this seems like a reasonable compromise.

            The alternative approach would be to have another thread that blocks waiting on
            schedule.get(), catching any Exceptions thrown, and passing them back up to the
            caller. However, the user would then be required to recreate subscriptions manually
            which isn't ideal given the aforementioned issues.
            */
            schedule = scheduledExecutorService.scheduleAtFixedRate(
                () -> {
                    try {
                        pollFilter(berithFilter);
                    } catch (Throwable e) {
                        // All exceptions must be caught, otherwise our job terminates without
                        // any notification
                        log.error("Error sending request", e);
                        callback.onError(e);
                    }
                },
                0, pollingInterval, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            throwException(e);
        }
    }

    private void getInitialFilterLogs() {
        try {
            final Optional<Request<?, BerithLog>> maybeRequest = getFilterLogs(filterId);
            final BerithLog berithLog;
            if (maybeRequest.isPresent()) {
                berithLog = maybeRequest.get().send();
            } else {
                berithLog = new BerithLog();
                berithLog.setResult(Collections.emptyList());
            }
            process(berithLog.getLogs());
        } catch (IOException e) {
            throwException(e);
        }
    }

    private void pollFilter(BerithFilter berithFilter) {
        try {
            BerithLog berithLog = caym.berith().berithGetFilterChanges(berithFilter.getFilterId()).send();
            if (berithLog.hasError()) {
                Error error = berithLog.getError();
                switch (error.getCode()) {
                    case RpcErrors.FILTER_NOT_FOUND:
                        reinstallFilter();
                        break;
                    default:
                        throwException(error);
                        break;
                }
            } else {
                process(berithLog.getLogs());
            }
        } catch (IOException e) {
            throwException(e);
        }
    }

    abstract BerithFilter sendRequest() throws IOException;

    abstract void process(List<LogResult> logResults);

    private void reinstallFilter() {
        log.warn("The filter has not been found. Filter id: " + filterId);
        schedule.cancel(true);
        run(scheduledExecutorService, pollingInterval);
    }

    public void cancel() {
        schedule.cancel(false);
        if (filterId == null) {
            return;
        }
        try {
            BerithUninstallFilter ethUninstallFilter = caym.berith().berithUninstallFilter(filterId).send();
            if (ethUninstallFilter.hasError()) {
                throwException(ethUninstallFilter.getError());
            }

            if (!ethUninstallFilter.isUninstalled()) {
                throw new FilterException("Filter with id '" + filterId + "' failed to uninstall");
            }
        } catch (IOException e) {
            throwException(e);
        }
    }

    /**
     * Retrieves historic filters for the filter with the given id.
     * Getting historic logs is not supported by all filters.
     * If not the method should return an empty EthLog object
     *
     * @param filterId Id of the filter for which the historic log should be retrieved
     * @return Historic logs, or an empty optional if the filter cannot retrieve historic logs
     */
    protected abstract Optional<Request<?, BerithLog>> getFilterLogs(BigInteger filterId);

    void throwException(Response.Error error) {
        throw new FilterException("Invalid request: "
            + (error == null ? "Unknown Error" : error.getMessage()));
    }

    void throwException(Throwable cause) {
        throw new FilterException("Error sending request", cause);
    }
}
