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

package berith.caym;

import java.util.Objects;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.http.HttpService;

/**
 * Implements of {@link Caym}
 */
public class CaymImpl implements Caym {

    private Web3jService web3jService;
    private Admin web3j;
    private Berith berith;

    CaymImpl() {
        this(DEFAULT_HTTP_URL);
    }

    CaymImpl(String httpUrl) {
        this(new HttpService(httpUrl));
    }

    CaymImpl(Web3jService web3jService) {
        this.web3jService = Objects.requireNonNull(web3jService, "web3jService");
        web3j = new JsonRpc2_0Admin(web3jService);
        berith = new JsonRpc2_0Berith(web3jService, web3j);
    }

    @Override
    public Berith berith() {
        return berith;
    }

    @Override
    public Web3jService getWeb3JService() {
        return web3jService;
    }
}
