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

package berith.caym.protocol.request;

import java.util.List;
import org.web3j.protocol.core.DefaultBlockParameter;

public class BerithFilterRequest extends FilterRequest<BerithFilterRequest> {

    /**
     * optional, params - defaults to latest for both
     */
    private DefaultBlockParameter fromBlock;
    /**
     * optional, params - defaults to latest for both
     */
    private DefaultBlockParameter toBlock;
    /**
     * Contract address or a list of addresses
     */
    private List<String> address;

    public BerithFilterRequest() {
    }

    public BerithFilterRequest(DefaultBlockParameter fromBlock, DefaultBlockParameter toBlock, List<String> address) {
        this.fromBlock = fromBlock;
        this.toBlock = toBlock;
        this.address = address;
    }

    public DefaultBlockParameter getFromBlock() {
        return fromBlock;
    }

    public DefaultBlockParameter getToBlock() {
        return toBlock;
    }

    public List<String> getAddress() {
        return address;
    }

    @Override
    BerithFilterRequest getThis() {
        return this;
    }
}
