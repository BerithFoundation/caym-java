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

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigInteger;
import org.web3j.utils.Numeric;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionRequest {

    public static final BigInteger DEFAULT_GAS = BigInteger.valueOf(9000);

    /**
     * the address the transaction is send from
     */
    private String from;
    /**
     * the address the transaction is directed to
     */
    private String to;
    /**
     * the gas provided for the transaction execution
     */
    private BigInteger gas;
    /**
     * the gasPrice used for each paid gas
     */
    private BigInteger gasPrice;
    /**
     * the value sent with this transaction
     */
    private BigInteger value;
    /**
     * the compiled code of a contract or the hash of the invoked method signature and encoded parameters
     */
    private String data;
    private BigInteger nonce;
    /**
     * from balance type
     */
    private Type base;
    /**
     * to balance type
     */
    private Type target;

    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null;  // we don't want the field to be encoded if not present
        }
    }

    /**
     * Returns a {@link TransactionRequest} for transfer transaction
     */
    public static TransactionRequest createBerithTransaction(String from, BigInteger nonce, BigInteger gasPrice,
        BigInteger gasLimit, String to, BigInteger value) {

        return new TransactionRequest(from, nonce, gasPrice, gasLimit, to, value, null, Type.MAIN, Type.MAIN);
    }

    /**
     * Returns a {@link TransactionRequest} for stake transaction
     */
    public static TransactionRequest createStakeTransaction(String from, BigInteger nonce, BigInteger gasPrice,
        BigInteger gasLimit, BigInteger value) {

        return new TransactionRequest(from, nonce, gasPrice, gasLimit, from, value, null, Type.MAIN, Type.STAKE);
    }

    /**
     * Returns a {@link TransactionRequest} for creating contract transaction
     */
    public static TransactionRequest createContractTransaction(String from, BigInteger nonce, BigInteger gasPrice,
        BigInteger gasLimit, BigInteger value, String init) {

        return new TransactionRequest(from, nonce, gasPrice, gasLimit, null, value, init, Type.MAIN, Type.STAKE);
    }

    /**
     * Returns a {@link TransactionRequest} for calling function transaction
     */
    public static TransactionRequest createFunctionCallTransaction(String from, BigInteger nonce, BigInteger gasPrice,
        BigInteger gasLimit, String to, BigInteger value, String data) {

        return new TransactionRequest(from, nonce, gasPrice, gasLimit, to, value, data, Type.MAIN, Type.MAIN);
    }


    public TransactionRequest(String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
        BigInteger value, String data, Type base, Type target) {

        this.from = from;
        this.to = to;
        this.gas = gasLimit;
        this.gasPrice = gasPrice;
        this.value = value;

        if (data != null) {
            this.data = Numeric.prependHexPrefix(data);
        }

        this.nonce = nonce;
        this.base = base;
        this.target = target;
    }

    public static BigInteger getDefaultGas() {
        return DEFAULT_GAS;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getGas() {
        return convert(gas);
    }

    public String getGasPrice() {
        return convert(gasPrice);
    }

    public String getValue() {
        return convert(value);
    }

    public String getData() {
        return data;
    }

    public String getNonce() {
        return convert(nonce);
    }

    public Type getBase() {
        return base;
    }

    public Type getTarget() {
        return target;
    }

    public enum Type {
        MAIN("MAIN"), STAKE("STAKE");

        private String value;


        Type(String value) {
            this.value = value;
        }
    }
}
