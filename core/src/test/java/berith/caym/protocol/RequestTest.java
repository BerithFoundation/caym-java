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

package berith.caym.protocol;

import berith.caym.Caym;
import berith.caym.protocol.request.BerithFilterRequest;
import berith.caym.protocol.request.TransactionRequest;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

/**
 * RPC request test
 */
public class RequestTest extends AbstractRequestTester {

    private Caym caym;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        caym = Caym.build(httpService);
    }

    @Test
    public void testWeb3ClientVersion() throws Exception {
        caym.berith().web3ClientVersion().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"web3_clientVersion\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testWeb3Sha3() throws Exception {
        caym.berith().web3Sha3("0x68656c6c6f20776f726c64").send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"web3_sha3\",\"params\":[\"0x68656c6c6f20776f726c64\"],\"id\":1}");
    }

    @Test
    public void testNetVersion() throws Exception {
        caym.berith().netVersion().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"net_version\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testNetPeerCount() throws Exception {
        caym.berith().netPeerCount().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"net_peerCount\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testNetListening() throws Exception {
        caym.berith().netListening().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"net_listening\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithProtocolVersion() throws Exception {
        caym.berith().berithProtocolVersion().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_protocolVersion\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithCoinbase() throws Exception {
        caym.berith().berithCoinbase().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_coinbase\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithSyncing() throws Exception {
        caym.berith().berithSyncing().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_syncing\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithMining() throws Exception {
        caym.berith().berithMining().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_mining\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithGasPrice() throws Exception {
        caym.berith().berithGasPrice().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_gasPrice\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithAccounts() throws Exception {
        caym.berith().berithAccounts().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_accounts\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithBlockNumber() throws Exception {
        caym.berith().berithBlockNumber().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_blockNumber\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithGetBalance() throws Exception {
        final String address = "Bxc94770007dda54cF92009BFF0dE90c06F603a09f";
        caym.berith().berithGetBalance(address, DefaultBlockParameterName.LATEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBalance\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"latest\"],\"id\":1}");

        caym.berith().berithGetBalance(address, DefaultBlockParameterName.EARLIEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBalance\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"earliest\"],\"id\":1}");

        caym.berith().berithGetBalance(address, DefaultBlockParameter.valueOf(BigInteger.valueOf(1))).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBalance\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"0x1\"],\"id\":1}");
    }

    @Test
    public void testBerithGetStakeBalance() throws Exception {
        final String address = "Bxc94770007dda54cF92009BFF0dE90c06F603a09f";
        caym.berith().berithGetStakeBalance(address, DefaultBlockParameterName.LATEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getStakeBalance\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"latest\"],\"id\":1}");

        caym.berith().berithGetStakeBalance(address, DefaultBlockParameterName.EARLIEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getStakeBalance\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"earliest\"],\"id\":1}");

        caym.berith().berithGetStakeBalance(address, DefaultBlockParameter.valueOf(BigInteger.valueOf(1))).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getStakeBalance\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"0x1\"],\"id\":1}");
    }

    @Test
    public void testBerithGetTransactionCount() throws Exception {
        final String address = "Bxc94770007dda54cF92009BFF0dE90c06F603a09f";
        caym.berith().berithGetTransactionCount(address, DefaultBlockParameterName.LATEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getTransactionCount\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"latest\"],\"id\":1}");

        caym.berith().berithGetTransactionCount(address, DefaultBlockParameterName.EARLIEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getTransactionCount\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"earliest\"],\"id\":1}");

        caym.berith().berithGetTransactionCount(address, DefaultBlockParameter.valueOf(BigInteger.valueOf(1))).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getTransactionCount\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"0x1\"],\"id\":1}");
    }

    @Test
    public void testBerithGetBlockTransactionCountByHash() throws Exception {
        final String blockHash = "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238";
        caym.berith().berithGetBlockTransactionCountByHash(blockHash).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBlockTransactionCountByHash\","
            + "\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
    }

    @Test
    public void testBerithGetBlockTransactionCountByNumber() throws Exception {
        caym.berith().berithGetBlockTransactionCountByNumber(DefaultBlockParameterName.LATEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBlockTransactionCountByNumber\","
            + "\"params\":[\"latest\"],\"id\":1}");

        caym.berith().berithGetBlockTransactionCountByNumber(DefaultBlockParameterName.EARLIEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBlockTransactionCountByNumber\","
            + "\"params\":[\"earliest\"],\"id\":1}");

        caym.berith().berithGetBlockTransactionCountByNumber(DefaultBlockParameter.valueOf(BigInteger.ONE)).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBlockTransactionCountByNumber\","
            + "\"params\":[\"0x1\"],\"id\":1}");
    }

    @Test
    public void testBerithGetUncleCountByBlockHash() throws Exception {
        final String blockHash = "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238";
        caym.berith().berithGetUncleCountByBlockHash(blockHash).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getUncleCountByBlockHash\","
            + "\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
    }

    @Test
    public void testBerithGetUncleCountByBlockNumber() throws Exception {
        caym.berith().berithGetUncleCountByBlockNumber(DefaultBlockParameterName.LATEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getUncleCountByBlockNumber\","
            + "\"params\":[\"latest\"],\"id\":1}");

        caym.berith().berithGetUncleCountByBlockNumber(DefaultBlockParameterName.EARLIEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getUncleCountByBlockNumber\","
            + "\"params\":[\"earliest\"],\"id\":1}");

        caym.berith().berithGetUncleCountByBlockNumber(DefaultBlockParameter.valueOf(BigInteger.ONE)).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getUncleCountByBlockNumber\","
            + "\"params\":[\"0x1\"],\"id\":1}");
    }

    @Test
    public void testBerithGetCode() throws Exception {
        final String address = "Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b";
        caym.berith().berithGetCode(address, DefaultBlockParameterName.LATEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getCode\","
            + "\"params\":[\"Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b\",\"latest\"],\"id\":1}");

        caym.berith().berithGetCode(address, DefaultBlockParameterName.EARLIEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getCode\","
            + "\"params\":[\"Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b\",\"earliest\"],\"id\":1}");

        caym.berith().berithGetCode(address, DefaultBlockParameter.valueOf(BigInteger.ONE)).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getCode\","
            + "\"params\":[\"Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b\",\"0x1\"],\"id\":1}");
    }

    @Test
    public void testBerithSign() throws Exception {
        final String address = "Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b";
        final String message = "0xdeadbeaf";
        caym.berith().berithSign(address, message).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_sign\","
            + "\"params\":[\"Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b\",\"0xdeadbeaf\"],\"id\":1}");
    }

    @Test
    public void testBerithSendTransaction() throws Exception {
        final TransactionRequest request = new TransactionRequest(
            "Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b",
            BigInteger.ONE,
            BigInteger.TEN,
            BigInteger.TEN,
            "Bxd46e8dd67c5d32be8058bb8eb970870f07244567",
            BigInteger.ONE,
            null,
            TransactionRequest.Type.MAIN,
            TransactionRequest.Type.MAIN
        );
        caym.berith().berithSendTransaction(request).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_sendTransaction\",\"params\":["
            + "{\"from\":\"Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b\","
            + "\"to\":\"Bxd46e8dd67c5d32be8058bb8eb970870f07244567\","
            + "\"gas\":\"0xa\","
            + "\"gasPrice\":\"0xa\","
            + "\"value\":\"0x1\","
            + "\"nonce\":\"0x1\","
            + "\"base\":\"MAIN\","
            + "\"target\":\"MAIN\"}"
            + "],\"id\":1}");
    }

    @Test
    public void testBerithSendRawTransaction() throws Exception {
        String rawTransaction = "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675";
        caym.berith().berithSendRawTransaction(rawTransaction).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_sendRawTransaction\",\"params\":["
            + "\"" + rawTransaction + "\"" + "],\"id\":1}");
    }

    @Test
    public void testBerithCall() throws Exception {
        TransactionRequest transactionRequest = TransactionRequest.createFunctionCallTransaction(
            "Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b",
            BigInteger.ONE,
            BigInteger.TEN,
            BigInteger.TEN,
            "Bxd46e8dd67c5d32be8058bb8eb970870f07244567",
            BigInteger.ZERO,
            "0xaaa"
        );
        caym.berith().berithCall(transactionRequest, DefaultBlockParameterName.LATEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_call\",\"params\":["
            + "{\"from\":\"Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b\","
            + "\"to\":\"Bxd46e8dd67c5d32be8058bb8eb970870f07244567\","
            + "\"gas\":\"0xa\","
            + "\"gasPrice\":\"0xa\","
            + "\"value\":\"0x0\","
            + "\"data\":\"0xaaa\","
            + "\"nonce\":\"0x1\","
            + "\"base\":\"MAIN\","
            + "\"target\":\"MAIN\"},"
            + "\"latest\"],\"id\":1}");
    }

    @Test
    public void testBerithEstimateGas() throws Exception {
        TransactionRequest transactionRequest = TransactionRequest.createFunctionCallTransaction(
            "Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b",
            BigInteger.ONE,
            BigInteger.TEN,
            BigInteger.TEN,
            "Bxd46e8dd67c5d32be8058bb8eb970870f07244567",
            BigInteger.ZERO,
            "0xaaa"
        );
        caym.berith().berithEstimateGas(transactionRequest).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_eth_estimateGas\",\"params\":["
            + "{\"from\":\"Bxa94f5374fce5edbc8e2a8697c15331677e6ebf0b\","
            + "\"to\":\"Bxd46e8dd67c5d32be8058bb8eb970870f07244567\","
            + "\"gas\":\"0xa\","
            + "\"gasPrice\":\"0xa\","
            + "\"value\":\"0x0\","
            + "\"data\":\"0xaaa\","
            + "\"nonce\":\"0x1\","
            + "\"base\":\"MAIN\","
            + "\"target\":\"MAIN\"}]"
            + ",\"id\":1}");
    }

    @Test
    public void testBerithGetBlockByHash() throws Exception {
        String blockHash = "0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7";
        caym.berith().berithGetBlockByHash(blockHash, true).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBlockByHash\",\"params\":"
            + "[\"0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7\",true],"
            + "\"id\":1}");
    }

    @Test
    public void testBerithGetBlockByNumber() throws Exception {
        caym.berith().berithGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBlockByNumber\",\"params\":[\"latest\",true],\"id\":1}");

        caym.berith().berithGetBlockByNumber(DefaultBlockParameterName.EARLIEST, false).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBlockByNumber\",\"params\":[\"earliest\",false],\"id\":1}");

        caym.berith().berithGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.ONE), false).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getBlockByNumber\",\"params\":[\"0x1\",false],\"id\":1}");
    }

    @Test
    public void testBerithGetTransactionByHash() throws Exception {
        String txHash = "0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b";
        caym.berith().berithGetTransactionByHash(txHash).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getTransactionByHash\","
            + "\"params\":[\"" + txHash + "\"],\"id\":1}");
    }

    @Test
    public void testBerithGetTransactionByBlockHashAndIndex() throws Exception {
        String blockHash = "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331";
        caym.berith().berithGetTransactionByBlockHashAndIndex(blockHash, BigInteger.ONE).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getTransactionByBlockHashAndIndex\","
            + "\"params\":[\"" + blockHash + "\",\"0x01\"],\"id\":1}");
    }

    @Test
    public void testBerithGetTransactionReceipt() throws Exception {
        String txHash = "0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b";
        caym.berith().berithGetTransactionReceipt(txHash).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getTransactionReceipt\","
            + "\"params\":[\"" + txHash + "\"],\"id\":1}");
    }

    @Test
    public void testBerithNewFilter() throws Exception {
        BerithFilterRequest filter = new BerithFilterRequest(
            DefaultBlockParameter.valueOf(BigInteger.ONE),
            DefaultBlockParameter.valueOf(BigInteger.TEN),
            Arrays.asList("Bxc94770007dda54cF92009BFF0dE90c06F603a09f")
        );
        caym.berith().berithNewFilter(filter).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_newFilter\",\"params\":[{\"topics\":[],"
            + "\"fromBlock\":\"0x1\",\"toBlock\":\"0xa\",\"address\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\"]}],\"id\":1}");
    }

    @Test
    public void testBerithNewBlockFilter() throws Exception {
        caym.berith().berithNewBlockFilter().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_newBlockFilter\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithNewPendingTransactionFilter() throws Exception {
        caym.berith().berithNewPendingTransactionFilter().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_newPendingTransactionFilter\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testBerithUninstallFilter() throws Exception {
        caym.berith().berithUninstallFilter(BigInteger.valueOf(10L)).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_uninstallFilter\",\"params\":[\"0x0a\"],\"id\":1}");
    }

    @Test
    public void testBerithGetFilterChanges() throws Exception {
        caym.berith().berithGetFilterChanges(BigInteger.valueOf(10L)).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getFilterChanges\",\"params\":[\"0x0a\"],\"id\":1}");
    }

    @Test
    public void testBerithGetFilterLogs() throws Exception {
        caym.berith().berithGetFilterLogs(BigInteger.valueOf(10L)).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getFilterLogs\",\"params\":[\"0x0a\"],\"id\":1}");
    }

    @Test
    public void testBerithGetLogs() throws Exception {
        BerithFilterRequest filter = new BerithFilterRequest(
            DefaultBlockParameter.valueOf(BigInteger.ONE),
            DefaultBlockParameter.valueOf(BigInteger.TEN),
            Collections.emptyList()
        );
        caym.berith().berithGetLogs(filter).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"berith_getLogs\",\"params\":[{\"topics\":[],"
            + "\"fromBlock\":\"0x1\",\"toBlock\":\"0xa\",\"address\":[]}],\"id\":1}");
    }

    @Test
    public void testBsrrGetBlockCreatorsByNumber() throws Exception {
        caym.berith().bsrrGetBlockCreatorsByNumber(DefaultBlockParameterName.LATEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"bsrr_getBlockCreatorsByNumber\","
            + "\"params\":[\"latest\"],\"id\":1}");

        caym.berith().bsrrGetBlockCreatorsByNumber(DefaultBlockParameterName.EARLIEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"bsrr_getBlockCreatorsByNumber\","
            + "\"params\":[\"earliest\"],\"id\":1}");

        caym.berith().bsrrGetBlockCreatorsByNumber(DefaultBlockParameter.valueOf(BigInteger.ONE)).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"bsrr_getBlockCreatorsByNumber\","
            + "\"params\":[\"0x1\"],\"id\":1}");
    }

    @Test
    public void testBsrrGetBlockCreatorsByHash() throws Exception {
        final String blockHash = "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238";
        caym.berith().bsrrGetBlockCreatorsByHash(blockHash).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"bsrr_getBlockCreatorsByHash\","
            + "\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
    }

    @Test
    public void testBsrrGetJoinRatio() throws Exception {
        final String address = "Bxc94770007dda54cF92009BFF0dE90c06F603a09f";
        caym.berith().bsrrGetJoinRatio(address, DefaultBlockParameterName.LATEST).send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"bsrr_getJoinRatio\","
            + "\"params\":[\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"latest\"],\"id\":1}");
    }
}
