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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import berith.caym.Caym;
import berith.caym.protocol.response.BerithFilter;
import berith.caym.protocol.response.BerithLog;
import berith.caym.protocol.response.BerithUninstallFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;

@RunWith(MockitoJUnitRunner.class)
public class BlockFilterTest {

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
    public void runTest() throws Exception {
        BerithLog berithLog = objectMapper.readValue(
            "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":["
                + "\"0x31c2342b1e0b8ffda1507fbffddf213c4b3c1e819ff6a84b943faabb0ebf2403\","
                + "\"0xccc0d2e07c1febcaca0c3341c4e1268204b06fefa4bb0c8c0d693d8e581c82da\""
                + "]}"
            , BerithLog.class);
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

        final CountDownLatch blockHashLatch = new CountDownLatch(2);
        final Set<String> actual = new HashSet<>();

        Callback<String> callback = new Callback<String>() {
            @Override
            public void onEvent(String event) {
                actual.add(event);
                blockHashLatch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                fail(throwable.getMessage());
            }
        };
        BlockFilter filter = new BlockFilter(caym, callback);

        // when
        filter.run(scheduledExecutorService, 500L);
        assertTrue(blockHashLatch.await(1L, TimeUnit.SECONDS));
        assertTrue(actual.contains("0x31c2342b1e0b8ffda1507fbffddf213c4b3c1e819ff6a84b943faabb0ebf2403"));
        assertTrue(actual.contains("0xccc0d2e07c1febcaca0c3341c4e1268204b06fefa4bb0c8c0d693d8e581c82da"));
    }
}
