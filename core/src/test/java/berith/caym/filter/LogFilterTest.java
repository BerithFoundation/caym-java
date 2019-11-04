/*
 * Copyright 2019 Berith foundation.
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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import berith.caym.Caym;
import berith.caym.protocol.request.BerithFilterRequest;
import berith.caym.protocol.response.BerithFilter;
import berith.caym.protocol.response.BerithLog;
import berith.caym.protocol.response.BerithUninstallFilter;
import berith.caym.protocol.response.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;

public class LogFilterTest {

    private Web3jService web3jService;
    private Caym caym;

    private final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Before
    public void setUp() {
        web3jService = mock(Web3jService.class);
        caym = Caym.build(web3jService);
    }

    @Test
    public void temp() throws Exception {

    }

    @Test
    public void runTest() throws Exception {
        String resultJson = "{ \n"
            + "   \"jsonrpc\":\"2.0\",\n"
            + "   \"id\":1,\n"
            + "   \"result\":[ \n"
            + "      { \n"
            + "         \"address\":\"0x2a98c5f40bfa3dee83431103c535f6fae9a8ad38\",\n"
            + "         \"blockHash\":\"0xd263df878c66b8a08c8509a8f33d6758bc3a1ee3c5ab3c9a765ea981ae9d72e3\",\n"
            + "         \"blockNumber\":\"0x2865a\",\n"
            + "         \"data\":\"0x45544855534400000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\n"
            + "         \"logIndex\":\"0x0\",\n"
            + "         \"removed\":false,\n"
            + "         \"topics\":[ \n"
            + "            \"0x5a690ecd0cb15c1c1fd6b6f8a32df0d4f56cb41a54fea7e94020f013595de796\",\n"
            + "            \"0x0000000000000000000000000000000000000000000000000000000000000002\",\n"
            + "            \"0x0000000000000000000000003f37a1c95bbc0aa6bf62e99b30b147e68dee7b43\",\n"
            + "            \"0x0000000000000000000000000000000000000000000000000000000000000000\"\n"
            + "         ],\n"
            + "         \"transactionHash\":\"0x9d8a4410204140a8fa7f191b0b1d9526822a87d212ade0b6a3cbd20a6e2ed2e9\",\n"
            + "         \"transactionIndex\":\"0x0\"\n"
            + "      },\n"
            + "      { \n"
            + "         \"address\":\"0x2a98c5f40bfa3dee83431103c535f6fae9a8ad38\",\n"
            + "         \"blockHash\":\"0xd263df878c66b8a08c8509a8f33d6758bc3a1ee3c5ab3c9a765ea981ae9d72e3\",\n"
            + "         \"blockNumber\":\"0x2865a\",\n"
            + "         \"data\":\"0x0000000000000000000000000000000000000000000000006c93a67534ce4000\",\n"
            + "         \"logIndex\":\"0x1\",\n"
            + "         \"removed\":false,\n"
            + "         \"topics\":[ \n"
            + "            \"0xa9c6cbc4bd352a6940479f6d802a1001550581858b310d7f68f7bea51218cda6\",\n"
            + "            \"0x4554485553440000000000000000000000000000000000000000000000000000\"\n"
            + "         ],\n"
            + "         \"transactionHash\":\"0x9d8a4410204140a8fa7f191b0b1d9526822a87d212ade0b6a3cbd20a6e2ed2e9\",\n"
            + "         \"transactionIndex\":\"0x0\"\n"
            + "      },\n"
            + "      { \n"
            + "         \"address\":\"0x3f37a1c95bbc0aa6bf62e99b30b147e68dee7b43\",\n"
            + "         \"blockHash\":\"0xd263df878c66b8a08c8509a8f33d6758bc3a1ee3c5ab3c9a765ea981ae9d72e3\",\n"
            + "         \"blockNumber\":\"0x2865a\",\n"
            + "         \"data\":\"0x0000000000000000000000000000000000000000000000006c93a67534ce4000\",\n"
            + "         \"logIndex\":\"0x2\",\n"
            + "         \"removed\":false,\n"
            + "         \"topics\":[ \n"
            + "            \"0xa609f6bd4ad0b4f419ddad4ac9f0d02c2b9295c5e6891469055cf73c2b568fff\",\n"
            + "            \"0x0000000000000000000000003f37a1c95bbc0aa6bf62e99b30b147e68dee7b43\"\n"
            + "         ],\n"
            + "         \"transactionHash\":\"0x9d8a4410204140a8fa7f191b0b1d9526822a87d212ade0b6a3cbd20a6e2ed2e9\",\n"
            + "         \"transactionIndex\":\"0x0\"\n"
            + "      },\n"
            + "      { \n"
            + "         \"address\":\"0x870283380c7da544d2b16d3434709874e3ed77cb\",\n"
            + "         \"blockHash\":\"0xd263df878c66b8a08c8509a8f33d6758bc3a1ee3c5ab3c9a765ea981ae9d72e3\",\n"
            + "         \"blockNumber\":\"0x2865a\",\n"
            + "         \"data\":\"0x000000000000000000000000000000000000000000000000000000005853a9f4000000000000000000000000000000000000000000000000000000000000004e\",\n"
            + "         \"logIndex\":\"0x3\",\n"
            + "         \"removed\":false,\n"
            + "         \"topics\":[ \n"
            + "            \"0x9de614b5c45179d41912c87ca7f7af67525f50c10b6b9cea79dc2c12b8d693a3\"\n"
            + "         ],\n"
            + "         \"transactionHash\":\"0xccf367f7c845de706ccf985a187ea168f9df241d4878780f996aa08a303ad910\",\n"
            + "         \"transactionIndex\":\"0x1\"\n"
            + "      },\n"
            + "      { \n"
            + "         \"address\":\"0x870283380c7da544d2b16d3434709874e3ed77cb\",\n"
            + "         \"blockHash\":\"0xd263df878c66b8a08c8509a8f33d6758bc3a1ee3c5ab3c9a765ea981ae9d72e3\",\n"
            + "         \"blockNumber\":\"0x2865a\",\n"
            + "         \"data\":\"0x000000000000000000000000000000000000000000000000000000005853aa04000000000000000000000000000000000000000000000000000000000000004e\",\n"
            + "         \"logIndex\":\"0x4\",\n"
            + "         \"removed\":false,\n"
            + "         \"topics\":[ \n"
            + "            \"0x9de614b5c45179d41912c87ca7f7af67525f50c10b6b9cea79dc2c12b8d693a3\"\n"
            + "         ],\n"
            + "         \"transactionHash\":\"0x83d38a70d55279be43cfb6da97256e438b6f72337be1a0919b349244fe633daa\",\n"
            + "         \"transactionIndex\":\"0x2\"\n"
            + "      }\n"
            + "   ]\n"
            + "}";
        BerithLog berithLog = objectMapper.readValue(resultJson, BerithLog.class);
        BerithFilter berithFilter = objectMapper.readValue(
            "{\"id\":1,\"jsonrpc\": \"2.0\",\"result\": \"0x1\"\n}",
            BerithFilter.class);
        BerithUninstallFilter berithUninstallFilter = objectMapper.readValue(
            "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":true}",
            BerithUninstallFilter.class);
        BerithLog notFoundFilter = objectMapper.readValue(
            "{\"jsonrpc\":\"2.0\",\"id\":1,\"error\":{\"code\":-32000,\"message\":\"filter not found\"}}",
            BerithLog.class);

        when(web3jService.send(any(Request.class), eq(BerithFilter.class))).thenReturn(berithFilter);
        when(web3jService.send(any(Request.class), eq(BerithLog.class))).thenReturn(berithLog)
            .thenReturn(notFoundFilter)
            .thenReturn(berithLog);
        when(web3jService.send(any(Request.class), eq(BerithUninstallFilter.class))).thenReturn(berithUninstallFilter);

        final CountDownLatch logLatch = new CountDownLatch(5);
        List<Log> actuals = new ArrayList<>();
        Callback<Log> callback = new Callback<Log>() {
            @Override
            public void onEvent(Log event) {
                logLatch.countDown();
                actuals.add(event);
            }

            @Override
            public void onError(Throwable throwable) {
                fail(throwable.getMessage());
            }
        };

        BerithFilterRequest filterRequest = new BerithFilterRequest().addSingleTopic("test");
        LogFilter filter = new LogFilter(caym, callback, filterRequest);
        filter.run(scheduledExecutorService, 500L);
        assertTrue(logLatch.await(1L, TimeUnit.SECONDS));
        filter.cancel();
        assertTrue(actuals.size() == 5);
    }
}
