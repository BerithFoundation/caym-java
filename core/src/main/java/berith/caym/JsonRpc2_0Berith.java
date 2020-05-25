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

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.BooleanResponse;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;

import berith.caym.protocol.request.BerithFilterRequest;
import berith.caym.protocol.request.TransactionRequest;
import berith.caym.protocol.response.BSRRBlockCreators;
import berith.caym.protocol.response.BSRRJoinRatio;
import berith.caym.protocol.response.BerithAccounts;
import berith.caym.protocol.response.BerithBlock;
import berith.caym.protocol.response.BerithCall;
import berith.caym.protocol.response.BerithCoinbase;
import berith.caym.protocol.response.BerithFilter;
import berith.caym.protocol.response.BerithGetCode;
import berith.caym.protocol.response.BerithGetTransactionReceipt;
import berith.caym.protocol.response.BerithLog;
import berith.caym.protocol.response.BerithProtocolVersion;
import berith.caym.protocol.response.BerithSendTransaction;
import berith.caym.protocol.response.BerithSign;
import berith.caym.protocol.response.BerithSyncing;
import berith.caym.protocol.response.BerithTransaction;
import berith.caym.protocol.response.BerithUninstallFilter;
import berith.caym.protocol.response.Quantity;
import berith.caym.util.NumericUtil;

/**
 * Implements of {@link Berith}
 */
public class JsonRpc2_0Berith implements Berith {

    protected final Web3jService web3jService;
    private final Admin web3j;

    public JsonRpc2_0Berith(Web3jService web3jService, Admin web3j) {
        this.web3jService = Objects.requireNonNull(web3jService, "web3jService");
        this.web3j = Objects.requireNonNull(web3j, "web3j");
    }

    @Override
    public Request<?, Web3ClientVersion> web3ClientVersion() {
        return new Request<>(
                "web3_clientVersion",
                Collections.<String>emptyList(),
                web3jService,
                Web3ClientVersion.class);
    }

    @Override
    public Request<?, Web3Sha3> web3Sha3(String data) {
        return new Request<>(
                "web3_sha3",
                Arrays.asList(data),
                web3jService,
                Web3Sha3.class);
    }

    @Override
    public Request<?, NetVersion> netVersion() {
        return new Request<>(
                "net_version",
                Collections.<String>emptyList(),
                web3jService,
                NetVersion.class);
    }

    @Override
    public Request<?, Quantity> netPeerCount() {
        return new Request<>(
                "net_peerCount",
                Collections.<String>emptyList(),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, BooleanResponse> netListening() {
        return new Request<>(
                "net_listening",
                Collections.<String>emptyList(),
                web3jService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, BerithProtocolVersion> berithProtocolVersion() {
        return new Request<>(
                "berith_protocolVersion",
                Collections.<String>emptyList(),
                web3jService,
                BerithProtocolVersion.class);
    }

    @Override
    public Request<?, BerithCoinbase> berithCoinbase() {
        return new Request<>(
                "berith_coinbase",
                Collections.<String>emptyList(),
                web3jService,
                BerithCoinbase.class);
    }

    @Override
    public Request<?, BerithSyncing> berithSyncing() {
        return new Request<>(
                "berith_syncing",
                Collections.<String>emptyList(),
                web3jService,
                BerithSyncing.class);
    }

    @Override
    public Request<?, BooleanResponse> berithMining() {
        return new Request<>(
                "berith_mining",
                Collections.<String>emptyList(),
                web3jService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, Quantity> berithGasPrice() {
        return new Request<>(
                "berith_gasPrice",
                Collections.<String>emptyList(),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, BerithAccounts> berithAccounts() {
        return new Request<>(
                "berith_accounts",
                Collections.<String>emptyList(),
                web3jService,
                BerithAccounts.class);
    }

    @Override
    public Request<?, Quantity> berithBlockNumber() {
        return new Request<>(
                "berith_blockNumber",
                Collections.<String>emptyList(),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, Quantity> berithGetBalance(String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "berith_getBalance",
                Arrays.asList(address, defaultBlockParameter),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, Quantity> berithGetStakeBalance(String address,
                                                      DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "berith_getStakeBalance",
                Arrays.asList(address, defaultBlockParameter),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, Quantity> berithGetTransactionCount(String address,
                                                          DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "berith_getTransactionCount",
                Arrays.asList(address, defaultBlockParameter),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, Quantity> berithGetBlockTransactionCountByHash(String blockHash) {
        return new Request<>(
                "berith_getBlockTransactionCountByHash",
                Arrays.asList(blockHash),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, Quantity> berithGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "berith_getBlockTransactionCountByNumber",
                Arrays.asList(defaultBlockParameter),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, Quantity> berithGetUncleCountByBlockHash(String blockHash) {
        return new Request<>(
                "berith_getUncleCountByBlockHash",
                Arrays.asList(blockHash),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, Quantity> berithGetUncleCountByBlockNumber(DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "berith_getUncleCountByBlockNumber",
                Arrays.asList(defaultBlockParameter),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, BerithGetCode> berithGetCode(String address,
                                                   DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "berith_getCode",
                Arrays.asList(address, defaultBlockParameter),
                web3jService,
                BerithGetCode.class);
    }

    @Override
    public Request<?, BerithSign> berithSign(String address, String sha3HashOfDataToSign) {
        return new Request<>(
                "berith_sign",
                Arrays.asList(address, sha3HashOfDataToSign),
                web3jService,
                BerithSign.class);
    }

    @Override
    public Request<?, BerithSendTransaction> berithSendTransaction(TransactionRequest transactionRequest) {
        return new Request<>(
                "berith_sendTransaction",
                Arrays.asList(transactionRequest),
                web3jService,
                BerithSendTransaction.class);
    }

    @Override
    public Request<?, BerithSendTransaction> berithSendRawTransaction(String rawTransaction) {
        return new Request<>(
                "berith_sendRawTransaction",
                Arrays.asList(rawTransaction),
                web3jService,
                BerithSendTransaction.class);
    }

    @Override
    public Request<?, BerithCall> berithCall(TransactionRequest transactionRequest,
                                             DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "berith_call",
                Arrays.asList(transactionRequest, defaultBlockParameter),
                web3jService,
                BerithCall.class);
    }

    @Override
    public Request<?, Quantity> berithEstimateGas(TransactionRequest transactionRequest) {
        return new Request<>(
                "berith_estimateGas",
                Arrays.asList(transactionRequest),
                web3jService,
                Quantity.class);
    }

    @Override
    public Request<?, BerithBlock> berithGetBlockByHash(String blockHash, boolean fullTransactionObjects) {
        return new Request<>(
                "berith_getBlockByHash",
                Arrays.asList(blockHash, fullTransactionObjects),
                web3jService,
                BerithBlock.class);
    }

    @Override
    public Request<?, BerithBlock> berithGetBlockByNumber(DefaultBlockParameter defaultBlockParameter,
                                                          boolean fullTransactionObjects) {
        return new Request<>(
                "berith_getBlockByNumber",
                Arrays.asList(defaultBlockParameter, fullTransactionObjects),
                web3jService,
                BerithBlock.class);
    }

    @Override
    public Request<?, BerithTransaction> berithGetTransactionByHash(String transactionHash) {
        return new Request<>(
                "berith_getTransactionByHash",
                Arrays.asList(transactionHash),
                web3jService,
                BerithTransaction.class);
    }

    @Override
    public Request<?, BerithTransaction> berithGetTransactionByBlockHashAndIndex(String blockHash,
                                                                                 BigInteger transactionIndex) {
        return new Request<>(
                "berith_getTransactionByBlockHashAndIndex",
                Arrays.asList(blockHash, NumericUtil.toHexStringWithPrefixSafe(transactionIndex)),
                web3jService,
                BerithTransaction.class);
    }

    @Override
    public Request<?, BerithTransaction> berithGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter,
            BigInteger transactionIndex) {
        return new Request<>(
                "berith_getTransactionByBlockNumberAndIndex",
                Arrays.asList(defaultBlockParameter, NumericUtil.toHexStringWithPrefixSafe(transactionIndex)),
                web3jService,
                BerithTransaction.class);
    }

    @Override
    public Request<?, BerithGetTransactionReceipt> berithGetTransactionReceipt(String transactionHash) {
        return new Request<>(
                "berith_getTransactionReceipt",
                Arrays.asList(transactionHash),
                web3jService,
                BerithGetTransactionReceipt.class);
    }

    @Override
    public Request<?, BerithFilter> berithNewFilter(BerithFilterRequest berithFilter) {
        return new Request<>(
                "berith_newFilter",
                Arrays.asList(berithFilter),
                web3jService,
                BerithFilter.class);
    }

    @Override
    public Request<?, BerithFilter> berithNewBlockFilter() {
        return new Request<>(
                "berith_newBlockFilter",
                Collections.<String>emptyList(),
                web3jService,
                BerithFilter.class);
    }

    @Override
    public Request<?, BerithFilter> berithNewPendingTransactionFilter() {
        return new Request<>(
                "berith_newPendingTransactionFilter",
                Collections.<String>emptyList(),
                web3jService,
                BerithFilter.class);
    }

    @Override
    public Request<?, BerithUninstallFilter> berithUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "berith_uninstallFilter",
                Arrays.asList(NumericUtil.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                BerithUninstallFilter.class);
    }

    @Override
    public Request<?, BerithLog> berithGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "berith_getFilterChanges",
                Arrays.asList(NumericUtil.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                BerithLog.class);
    }

    @Override
    public Request<?, BerithLog> berithGetFilterLogs(BigInteger filterId) {
        return new Request<>(
                "berith_getFilterLogs",
                Arrays.asList(NumericUtil.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                BerithLog.class);
    }

    @Override
    public Request<?, BerithLog> berithGetLogs(BerithFilterRequest berithFilterRequest) {
        return new Request<>(
                "berith_getLogs",
                Arrays.asList(berithFilterRequest),
                web3jService,
                BerithLog.class);
    }

    @Override
    public Request<?, BSRRBlockCreators> bsrrGetBlockCreatorsByNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "bsrr_getBlockCreatorsByNumber",
                Arrays.asList(defaultBlockParameter),
                web3jService,
                BSRRBlockCreators.class);
    }

    @Override
    public Request<?, BSRRBlockCreators> bsrrGetBlockCreatorsByHash(String blockHash) {
        return new Request<>(
                "bsrr_getBlockCreatorsByHash",
                Arrays.asList(blockHash),
                web3jService,
                BSRRBlockCreators.class);
    }

    @Override
    public Request<?, BSRRJoinRatio> bsrrGetJoinRatio(String address,
                                                      DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "bsrr_getJoinRatio",
                Arrays.asList(address, defaultBlockParameter),
                web3jService,
                BSRRJoinRatio.class);
    }
}
