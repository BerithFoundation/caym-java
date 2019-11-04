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
import berith.caym.protocol.request.BerithFilterRequest;
import berith.caym.protocol.response.BerithFilter;
import berith.caym.protocol.response.BerithLog;
import berith.caym.protocol.response.BerithLog.LogObject;
import berith.caym.protocol.response.BerithLog.LogResult;
import berith.caym.protocol.response.Log;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.web3j.protocol.core.Request;

/**
 * Log filter handler.
 */
public class LogFilter extends Filter<Log> {

    private BerithFilterRequest berithFilter;

    public LogFilter(Caym caym, Callback<Log> callback, BerithFilterRequest berithFilter) {
        super(caym, callback);
        this.berithFilter = berithFilter;
    }

    @Override
    BerithFilter sendRequest() throws IOException {
        return caym.berith().berithNewFilter(berithFilter).send();
    }

    @Override
    void process(List<LogResult> logResults) {
        for (LogResult logResult : logResults) {
            if (logResult instanceof LogObject) {
                Log log = ((LogObject) logResult).get();
                callback.onEvent(log);
            } else {
                callback.onError(new FilterException("Unexpected result type: " + logResult.get() + " required LogObject"));
            }
        }
    }

    @Override
    protected Optional<Request<?, BerithLog>> getFilterLogs(BigInteger filterId) {
        return Optional.of(caym.berith().berithGetFilterLogs(filterId));
    }
}
