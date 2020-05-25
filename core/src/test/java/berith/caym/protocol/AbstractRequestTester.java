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

package berith.caym.protocol;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import org.junit.Before;
import org.web3j.protocol.http.HttpService;

public abstract class AbstractRequestTester {

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient httpClient;
    private HttpService httpService;

    private RequestInterceptor requestInterceptor;

    @Before
    public void setUp() {
        requestInterceptor = new RequestInterceptor();
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build();
        httpService = new HttpService(httpClient);
        initWeb3Client(httpService);
    }

    protected abstract void initWeb3Client(HttpService httpService);

    protected void verifyResult(String expected) throws Exception {
        RequestBody requestBody = requestInterceptor.getRequestBody();
        assertNotNull(requestBody);
        assertThat(requestBody.contentType(), is(HttpService.JSON_MEDIA_TYPE));

        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        assertThat(replaceRequestId(buffer.readUtf8()), is(replaceRequestId(expected)));
    }

    private String replaceRequestId(String json) {
        return json.replaceAll("\"id\":\\d*}$", "\"id\":<generatedValue>}");
    }

    private class RequestInterceptor implements Interceptor {

        private RequestBody requestBody;

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            requestBody = request.body();

            Response response = new Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_2)
                    .code(200)
                    .body(
                            ResponseBody.create(
                                    "{\"jsonrpc\":\"2.0\",\"method\":\"\","
                                    + "\"params\":[],\"id\":1}",
                                    JSON_MEDIA_TYPE))
                    .message("")
                    .build();

            return response;
        }

        public RequestBody getRequestBody() {
            return requestBody;
        }
    }
}
