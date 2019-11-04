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
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.filters.FilterException;

/**
 * Handler for working with transaction filter requests.
 */
public class PendingTransactionFilter extends Filter<String> {

    public PendingTransactionFilter(Caym caym, Callback<String> callback) {
        super(caym, callback);
    }

    @Override
    BerithFilter sendRequest() throws IOException {
        return caym.berith().berithNewPendingTransactionFilter().send();
    }

    @Override
    void process(List<LogResult> logResults) {
        for (LogResult logResult : logResults) {
            if (logResult instanceof BerithLog.Hash) {
                String transactionHash = ((BerithLog.Hash) logResult).get();
                callback.onEvent(transactionHash);
            } else {
                callback.onError(new FilterException("Unexpected result type: " + logResult.get() + ", required Hash"));
            }
        }
    }

    /**
     * Since the pending transaction filter does not support historic filters,
     * the filterId is ignored and an empty optional is returned
     *
     * @param filterId Id of the filter for which the historic log should be retrieved
     * @return Optional.empty()
     */
    @Override
    protected Optional<Request<?, BerithLog>> getFilterLogs(BigInteger filterId) {
        return Optional.empty();
    }
}
