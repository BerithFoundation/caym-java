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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import berith.caym.protocol.response.BSRRBlockCreators;
import berith.caym.protocol.response.BSRRJoinRatio;
import berith.caym.protocol.response.BerithAccounts;
import berith.caym.protocol.response.BerithBlock;
import berith.caym.protocol.response.BerithBlock.Block;
import berith.caym.protocol.response.BerithBlock.TransactionObject;
import berith.caym.protocol.response.BerithBlock.TransactionResult;
import berith.caym.protocol.response.BerithCall;
import berith.caym.protocol.response.BerithCoinbase;
import berith.caym.protocol.response.BerithFilter;
import berith.caym.protocol.response.BerithGetCode;
import berith.caym.protocol.response.BerithGetTransactionReceipt;
import berith.caym.protocol.response.BerithLog;
import berith.caym.protocol.response.BerithLog.LogObject;
import berith.caym.protocol.response.BerithLog.LogResult;
import berith.caym.protocol.response.BerithProtocolVersion;
import berith.caym.protocol.response.BerithSendTransaction;
import berith.caym.protocol.response.BerithSyncing;
import berith.caym.protocol.response.BerithTransaction;
import berith.caym.protocol.response.Quantity;
import berith.caym.protocol.response.Transaction;
import berith.caym.protocol.response.TransactionReceipt;
import java.math.BigInteger;
import org.junit.Test;
import org.web3j.protocol.admin.methods.response.BooleanResponse;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.NetListening;
import org.web3j.protocol.core.methods.response.NetPeerCount;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import org.web3j.utils.Numeric;

public class ResponseTest extends AbstractResponseTester {

    @Test
    public void testErrorResponse() {
        buildResponse(
            "{"
                + "  \"jsonrpc\":\"2.0\","
                + "  \"id\":1,"
                + "  \"error\":{"
                + "    \"code\":-32602,"
                + "    \"message\":\"Invalid address length, expected 40 got 64 bytes\","
                + "    \"data\":null"
                + "  }"
                + "}");

        BerithBlock berithBlock = deserialiseResponse(BerithBlock.class);
        assertTrue(berithBlock.hasError());
        Response.Error expectedErr = new Response.Error(-32602, "Invalid address length, expected 40 got 64 bytes");
        assertThat(berithBlock.getError(), equalTo(expectedErr));
    }

    @Test
    public void testErrorResponseComplexData() {
        buildResponse(
            "{"
                + "  \"jsonrpc\":\"2.0\","
                + "  \"id\":1,"
                + "  \"error\":{"
                + "    \"code\":-32602,"
                + "    \"message\":\"Invalid address length, expected 40 got 64 bytes\","
                + "    \"data\":{\"foo\":\"bar\"}"
                + "  }"
                + "}");

        BerithBlock berithBlock = deserialiseResponse(BerithBlock.class);
        assertTrue(berithBlock.hasError());
        assertThat(berithBlock.getError().getData(), equalTo("{\"foo\":\"bar\"}"));
    }

    @Test
    public void testBooleanResponse() {
        // includes
        // - berith_mining
        // - berith_uninstallFilter
        buildResponse("{\n"
            + "  \"id\":71,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": true\n"
            + "}");
        BooleanResponse response = deserialiseResponse(BooleanResponse.class);
        assertTrue(response.success());
    }

    @Test
    public void testQuantityResponse() {
        // includes
        // - berith_gasPrice
        // - berith_blockNumber
        // - berith_getBalance
        // - berith_getStakeBalance
        // - berith_getTransactionCount
        // - berith_getBlockTransactionCountByHash
        // - berith_getBlockTransactionCountByNumber
        // - berith_getUncleCountByBlockHash
        // - berith_getUncleCountByBlockNumber
        // - berith_estimateGas
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"0xa9184e72a000\""
            + "}");
        Quantity quantity = deserialiseResponse(Quantity.class);
        assertThat(quantity.getValue().toString(16), is("a9184e72a000"));
    }

    @Test
    public void testWeb3ClientVersion() {
        buildResponse(
            "{\n"
                + "  \"id\":67,\n"
                + "  \"jsonrpc\":\"2.0\",\n"
                + "  \"result\": \"Berith/v0.1.0-unstable-c6469980/linux-amd64/go1.12.1\"\n"
                + "}");

        Web3ClientVersion web3ClientVersion = deserialiseResponse(Web3ClientVersion.class);
        assertThat(web3ClientVersion.getWeb3ClientVersion(), is("Berith/v0.1.0-unstable-c6469980/linux-amd64/go1.12.1"));
    }

    @Test
    public void testWeb3Sha3() {
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad\"\n"
            + "}");
        Web3Sha3 web3Sha3 = deserialiseResponse(Web3Sha3.class);
        assertThat(web3Sha3.getResult(), is("0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad"));
    }

    @Test
    public void testNetVersion() {
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"3\"\n"
            + "}");
        NetVersion netVersion = deserialiseResponse(NetVersion.class);
        assertThat(netVersion.getNetVersion(), is("3"));
    }

    @Test
    public void testNetPeerCount() {
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"0x2\"\n"
            + "}");
        NetPeerCount peerCount = deserialiseResponse(NetPeerCount.class);
        assertThat(peerCount.getQuantity().toString(16), is("2"));
    }

    @Test
    public void testNetListening() {
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\":\"2.0\",\n"
            + "  \"result\":true\n"
            + "}");
        NetListening netListening = deserialiseResponse(NetListening.class);
        assertTrue(netListening.isListening());
    }

    @Test
    public void testBerithProtocolVersion() {
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"0x3f\"\n"
            + "}");
        BerithProtocolVersion berithProtocolVersion = deserialiseResponse(BerithProtocolVersion.class);
        assertThat(berithProtocolVersion.getProtocolVersion(), is("0x3f"));
    }

    @Test
    public void testBerithCoinbase() {
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e\"\n"
            + "}");
        BerithCoinbase berithCoinbase = deserialiseResponse(BerithCoinbase.class);
        assertThat(berithCoinbase.getCoinbase(), is("Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e"));
    }

    @Test
    public void testBerithSyncing() {
        buildResponse(
            "{\n"
                + "  \"id\":1,\n"
                + "  \"jsonrpc\": \"2.0\",\n"
                + "  \"result\": {\n"
                + "  \"startingBlock\": \"0x384\",\n"
                + "  \"currentBlock\": \"0x386\",\n"
                + "  \"highestBlock\": \"0x454\"\n"
                + "  }\n"
                + "}");
        BerithSyncing berithSyncing = deserialiseResponse(BerithSyncing.class);
        assertTrue(berithSyncing.isSyncing());
        assertTrue(berithSyncing.getResult() instanceof BerithSyncing.Syncing);
        BerithSyncing.Syncing sync = (BerithSyncing.Syncing) berithSyncing.getResult();
        assertThat(sync.getStartingBlock(), is("0x384"));
        assertThat(sync.getCurrentBlock(), is("0x386"));
        assertThat(sync.getHighestBlock(), is("0x454"));

        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": false\n"
            + "}");
        berithSyncing = deserialiseResponse(BerithSyncing.class);
        assertFalse(berithSyncing.isSyncing());
    }

    @Test
    public void testBerithAccounts() {
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": [\"Bxc94770007dda54cF92009BFF0dE90c06F603a09f\",\"Bx04668Ec2f57cC15c381b461B9fEDaB5D451c8F7F\"]\n"
            + "}");
        BerithAccounts berithAccounts = deserialiseResponse(BerithAccounts.class);
        assertThat(berithAccounts.getAccounts().size(), is(2));
        assertTrue(berithAccounts.getAccounts().contains("Bxc94770007dda54cF92009BFF0dE90c06F603a09f"));
        assertTrue(berithAccounts.getAccounts().contains("Bx04668Ec2f57cC15c381b461B9fEDaB5D451c8F7F"));
    }

    @Test
    public void testBerithGetCode() {
        buildResponse("{\n"
            + "  \"id\": 1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"0x600160008035811a818181146012578301005b601b6001356025565b8060005260206000f25b600060078202905091905056\"\n"
            + "}");
        BerithGetCode berithGetCode = deserialiseResponse(BerithGetCode.class);
        assertThat(berithGetCode.getCode(),
            is("0x600160008035811a818181146012578301005b601b6001356025565b8060005260206000f25b600060078202905091905056"));
    }

    @Test
    public void testBerithSendTransaction() {
        // includes
        // - berith_sendTransaction
        // - berith_sendRawTransaction
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"0xcc7b69665bb876991b7621a4c225993f3c789a7647871f71350d9f7153583039\"\n"
            + "}");
        BerithSendTransaction berithSendTransaction = deserialiseResponse(BerithSendTransaction.class);
        assertThat(berithSendTransaction.getTransactionHash(), is("0xcc7b69665bb876991b7621a4c225993f3c789a7647871f71350d9f7153583039"));
    }

    @Test
    public void testBerithCall() {
        buildResponse("{\n"
            + "  \"id\": 1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"0x\"\n"
            + "}");
        BerithCall berithCall = deserialiseResponse(BerithCall.class);
        assertThat(berithCall.getValue(), is("0x"));
    }

    @Test
    public void testBerithGetBlock() {
        // includes
        // - berith_getBlockByHash
        // - berith_getBlockByNumber
        // -
        buildResponse("{\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"id\": 1,\n"
            + "  \"result\": {\n"
            + "    \"difficulty\": \"0x5265c0\",\n"
            + "    \"extraData\": \"0xd9820100866265\",\n"
            + "    \"gasLimit\": \"0x5f65c42\",\n"
            + "    \"gasUsed\": \"0x5208\",\n"
            + "    \"hash\": \"0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7\",\n"
            + "    \"logsBloom\": \"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\n"
            + "    \"miner\": \"Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e\",\n"
            + "    \"mixHash\": \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
            + "    \"nonce\": \"0x0000000000000001\",\n"
            + "    \"number\": \"0x850\",\n"
            + "    \"parentHash\": \"0x4b297ac504bb9a7d74a161a3f45f48717a5acf0aaf16e1e6d2faf4b34a4ffcd9\",\n"
            + "    \"receiptsRoot\": \"0x056b23fbba480696b65fe5a59b8f2148a1299103c4f57df839233af2cf4ca2d2\",\n"
            + "    \"sha3Uncles\": \"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347\",\n"
            + "    \"size\": \"0x2ce\",\n"
            + "    \"stateRoot\": \"0x416718a61a1aedf6c189fa2a87974370e0824cd116aedf82853dedf220496892\",\n"
            + "    \"timestamp\": \"0x5dba6667\",\n"
            + "    \"totalDifficulty\": \"0x2a9b602b5\",\n"
            + "    \"transactions\": [\n"
            + "      {\n"
            + "        \"blockHash\": \"0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7\",\n"
            + "        \"blockNumber\": \"0x850\",\n"
            + "        \"from\": \"Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e\",\n"
            + "        \"gas\": \"0x15f90\",\n"
            + "        \"gasPrice\": \"0x1\",\n"
            + "        \"hash\": \"0xcfe05741f5c30a50ceab4e847eb4f48ed7b0b9841c92e1aaaeaef12226950ade\",\n"
            + "        \"input\": \"0x\",\n"
            + "        \"nonce\": \"0x4\",\n"
            + "        \"to\": \"Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e\",\n"
            + "        \"transactionIndex\": \"0x0\",\n"
            + "        \"value\": \"0x1\",\n"
            + "        \"base\": 1,\n"
            + "        \"target\": 1,\n"
            + "        \"v\": \"0x11cca\",\n"
            + "        \"r\": \"0x6b69f34217fb9ffccee69719719ecc1afe73d634d70b7c9834b467d2afc78921\",\n"
            + "        \"s\": \"0x526c6133e1e400989e24365f9bb06bec009a4526be572417a86e5a96c56814d3\"\n"
            + "      }\n"
            + "    ],\n"
            + "    \"transactionsRoot\": \"0xc40267b67d6b4d1930cb54f913c69c6560dd35633d67a4921e6803f3dbf7ef84\",\n"
            + "    \"uncles\": []\n"
            + "  }\n"
            + "}");
        BerithBlock berithBlock = deserialiseResponse(BerithBlock.class);
        // assertions of block
        assertNotNull(berithBlock.getBlock());
        Block block = berithBlock.getBlock();
        assertBigInteger(block.getDifficulty(), "5265c0");
        assertThat(block.getExtraData(), is("0xd9820100866265"));
        assertBigInteger(block.getGasLimit(), "5f65c42");
        assertBigInteger(block.getGasUsed(), "5208");
        assertThat(block.getHash(), is("0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7"));
        assertThat(block.getLogsBloom(),
            is("0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        assertThat(block.getMixHash(), is("0x0000000000000000000000000000000000000000000000000000000000000000"));
        assertBigInteger(block.getNonce(), "0x1");
        assertBigInteger(block.getNumber(), "0x850");
        assertThat(block.getParentHash(), is("0x4b297ac504bb9a7d74a161a3f45f48717a5acf0aaf16e1e6d2faf4b34a4ffcd9"));
        assertThat(block.getReceiptsRoot(), is("0x056b23fbba480696b65fe5a59b8f2148a1299103c4f57df839233af2cf4ca2d2"));
        assertThat(block.getSha3Uncles(), is("0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347"));
        assertBigInteger(block.getSize(), "0x2ce");
        assertThat(block.getStateRoot(), is("0x416718a61a1aedf6c189fa2a87974370e0824cd116aedf82853dedf220496892"));
        assertBigInteger(block.getTimestamp(), "0x5dba6667");
        assertBigInteger(block.getTotalDifficulty(), "0x2a9b602b5");
        assertThat(block.getTransactionsRoot(), is("0xc40267b67d6b4d1930cb54f913c69c6560dd35633d67a4921e6803f3dbf7ef84"));
        assertTrue(block.getUncles().isEmpty());

        // assertions of transaction
        assertTrue(block.getTransactions().size() == 1);
        TransactionResult transactionResult = block.getTransactions().get(0);
        assertTrue(transactionResult instanceof TransactionObject);
        TransactionObject tx = (TransactionObject) transactionResult;
        assertThat(tx.getBlockHash(), is("0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7"));
        assertBigInteger(tx.getBlockNumber(), "0x850");
        assertThat(tx.getFrom(), is("Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e"));
        assertBigInteger(tx.getGas(), "0x15f90");
        assertBigInteger(tx.getGasPrice(), "0x1");
        assertThat(tx.getHash(), is("0xcfe05741f5c30a50ceab4e847eb4f48ed7b0b9841c92e1aaaeaef12226950ade"));
        assertThat(tx.getInput(), is("0x"));
        assertBigInteger(tx.getNonce(), "0x4");
        assertThat(tx.getTo(), is("Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e"));
        assertBigInteger(tx.getTransactionIndex(), "0x0");
        assertBigInteger(tx.getValue(), "0x1");
        assertThat(tx.getBase(), is(Transaction.Type.MAIN));
        assertThat(tx.getTarget(), is(Transaction.Type.MAIN));
        assertThat(tx.getV(), is(hexToLong("0x11cca")));
        assertThat(tx.getR(), is("0x6b69f34217fb9ffccee69719719ecc1afe73d634d70b7c9834b467d2afc78921"));
        assertThat(tx.getS(), is("0x526c6133e1e400989e24365f9bb06bec009a4526be572417a86e5a96c56814d3"));
    }

    @Test
    public void testBerithTransaction() {
        // includes
        // - berith_getTransactionByHash
        // - berith_getTransactionByBlockHashAndIndex
        // -
        buildResponse("{\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"id\": 1,\n"
            + "  \"result\": {\n"
            + "    \"blockHash\": \"0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7\",\n"
            + "    \"blockNumber\": \"0x850\",\n"
            + "    \"from\": \"Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e\",\n"
            + "    \"gas\": \"0x15f90\",\n"
            + "    \"gasPrice\": \"0x1\",\n"
            + "    \"hash\": \"0xcfe05741f5c30a50ceab4e847eb4f48ed7b0b9841c92e1aaaeaef12226950ade\",\n"
            + "    \"input\": \"0x\",\n"
            + "    \"nonce\": \"0x4\",\n"
            + "    \"to\": \"Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e\",\n"
            + "    \"transactionIndex\": \"0x0\",\n"
            + "    \"value\": \"0x1\",\n"
            + "    \"base\": 1,\n"
            + "    \"target\": 1,\n"
            + "    \"v\": \"0x11cca\",\n"
            + "    \"r\": \"0x6b69f34217fb9ffccee69719719ecc1afe73d634d70b7c9834b467d2afc78921\",\n"
            + "    \"s\": \"0x526c6133e1e400989e24365f9bb06bec009a4526be572417a86e5a96c56814d3\"\n"
            + "  }\n"
            + "}");
        BerithTransaction transaction = deserialiseResponse(BerithTransaction.class);
        assertTrue(transaction.getTransaction().isPresent());
        Transaction tx = transaction.getTransaction().get();
        assertThat(tx.getBlockHash(), is("0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7"));
        assertBigInteger(tx.getBlockNumber(), "0x850");
        assertThat(tx.getFrom(), is("Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e"));
        assertBigInteger(tx.getGas(), "0x15f90");
        assertBigInteger(tx.getGasPrice(), "0x1");
        assertThat(tx.getHash(), is("0xcfe05741f5c30a50ceab4e847eb4f48ed7b0b9841c92e1aaaeaef12226950ade"));
        assertThat(tx.getInput(), is("0x"));
        assertBigInteger(tx.getNonce(), "0x4");
        assertThat(tx.getTo(), is("Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e"));
        assertBigInteger(tx.getTransactionIndex(), "0x0");
        assertBigInteger(tx.getValue(), "0x1");
        assertThat(tx.getBase(), is(Transaction.Type.MAIN));
        assertThat(tx.getTarget(), is(Transaction.Type.MAIN));
        // TODO r, v, s
        assertThat(tx.getV(), is(hexToLong("0x11cca")));
        assertThat(tx.getR(), is("0x6b69f34217fb9ffccee69719719ecc1afe73d634d70b7c9834b467d2afc78921"));
        assertThat(tx.getS(), is("0x526c6133e1e400989e24365f9bb06bec009a4526be572417a86e5a96c56814d3"));
    }

    @Test
    public void testBerithGetTransactionReceipt() {
        buildResponse("{\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"id\": 1,\n"
            + "  \"result\": {\n"
            + "    \"blockHash\": \"0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7\",\n"
            + "    \"blockNumber\": \"0x850\",\n"
            + "    \"contractAddress\": null,\n"
            + "    \"cumulativeGasUsed\": \"0x5208\",\n"
            + "    \"from\": \"Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e\",\n"
            + "    \"gasUsed\": \"0x5208\",\n"
            + "    \"logs\": [],\n"
            + "    \"logsBloom\": \"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\n"
            + "    \"status\": \"0x1\",\n"
            + "    \"to\": \"Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e\",\n"
            + "    \"transactionHash\": \"0xcfe05741f5c30a50ceab4e847eb4f48ed7b0b9841c92e1aaaeaef12226950ade\",\n"
            + "    \"transactionIndex\": \"0x0\"\n"
            + "  }\n"
            + "}");
        BerithGetTransactionReceipt transactionReceipt = deserialiseResponse(BerithGetTransactionReceipt.class);
        assertTrue(transactionReceipt.getTransactionReceipt().isPresent());
        TransactionReceipt tr = transactionReceipt.getTransactionReceipt().get();
        assertThat(tr.getBlockHash(), is("0xd4c5d0d4b37e7636617ea0731e0bd03395605654bb7cafe11663234b7e9e23c7"));
        assertBigInteger(tr.getBlockNumber(), "0x850");
        assertNull(tr.getContractAddress());
        assertBigInteger(tr.getCumulativeGasUsed(), "0x5208");
        assertBigInteger(tr.getGasUsed(), "0x5208");
        assertTrue(tr.getLogs().isEmpty());
        assertThat(tr.getLogsBloom(),
            is("0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        assertThat(tr.getStatus(), is("0x1"));
        assertThat(tr.getTo(), is("Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e"));
        assertThat(tr.getTransactionHash(), is("0xcfe05741f5c30a50ceab4e847eb4f48ed7b0b9841c92e1aaaeaef12226950ade"));
        assertBigInteger(tr.getTransactionIndex(), "0x0");
    }

    @Test
    public void testBerithFilter() {
        // includes
        // - berith_newFilter
        // - berith_newBlockFilter
        buildResponse("{\n"
            + "  \"id\":1,\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"result\": \"0x94f027546179fe922429e172b74bbba0\"\n"
            + "}");
        BerithFilter filter = deserialiseResponse(BerithFilter.class);
        assertBigInteger(filter.getFilterId(), "0x94f027546179fe922429e172b74bbba0");
    }

    @Test
    public void testBerithLog() {
        buildResponse(
            "{\n"
                + "    \"id\":1,\n"
                + "    \"jsonrpc\":\"2.0\",\n"
                + "    \"result\": [{\n"
                + "        \"removed\": false,\n"
                + "        \"logIndex\": \"0x1\",\n"
                + "        \"transactionIndex\": \"0x0\",\n"
                + "        \"transactionHash\": \"0xdf829c5a142f1fccd7d8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcf\",\n"
                + "        \"blockHash\": \"0x8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcfdf829c5a142f1fccd7d\",\n"
                + "        \"blockNumber\":\"0x1b4\",\n"
                + "        \"address\": \"0x16c5785ac562ff41e2dcfdf829c5a142f1fccd7d\",\n"
                + "        \"data\":\"0x0000000000000000000000000000000000000000000000000000000000000000\",\n"
                + "        \"type\":\"mined\",\n"
                + "        \"topics\": [\"0x59ebeb90bc63057b6515673c3ecf9438e5058bca0f92585014eced636878c9a5\"]"
                + "    }]"
                + "}"
        );
        BerithLog berithLog = deserialiseResponse(BerithLog.class);
        assertTrue(berithLog.getLogs().size() == 1);
        LogResult logResult = berithLog.getLogs().get(0);
        assertTrue(logResult instanceof LogObject);
        LogObject logObject = (LogObject) logResult;
        assertFalse(logObject.isRemoved());
        assertBigInteger(logObject.getLogIndex(), "0x1");
        assertBigInteger(logObject.getTransactionIndex(), "0x0");
        assertThat(logObject.getTransactionHash(), is("0xdf829c5a142f1fccd7d8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcf"));
        assertThat(logObject.getBlockHash(), is("0x8216c5785ac562ff41e2dcfdf5785ac562ff41e2dcfdf829c5a142f1fccd7d"));
        assertBigInteger(logObject.getBlockNumber(), "0x1b4");
        assertThat(logObject.getAddress(), is("0x16c5785ac562ff41e2dcfdf829c5a142f1fccd7d"));
        assertThat(logObject.getData(), is("0x0000000000000000000000000000000000000000000000000000000000000000"));
        assertThat(logObject.getType(), is("mined"));
        assertTrue(logObject.getTopics().size() == 1);
        assertThat(logObject.getTopics().get(0), is("0x59ebeb90bc63057b6515673c3ecf9438e5058bca0f92585014eced636878c9a5"));
    }

    @Test
    public void testBsrrBlockCreators() throws Exception {
        // includes
        // - bsrr_getBlockCreatorsByNumber
        // - bsrr_getBlockCreatorsByHash
        buildResponse("{\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"id\": 1,\n"
            + "  \"result\": [\n"
            + "    \"Bxca7207de79e55c1a69dbc67a4a2e81dfc62c6ac4\",\n"
            + "    \"Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e\",\n"
            + "    \"Bx90865e6e6737fe766dd08f39cc2cf1550b5f3875\",\n"
            + "    \"Bxbb926bbb0b15ca54d4a19dcdf44fc8940e3f6da3\",\n"
            + "    \"Bx8676fb254279ef78c53b8a781e228ab439065786\"\n"
            + "  ]\n"
            + "}");
        BSRRBlockCreators creators = deserialiseResponse(BSRRBlockCreators.class);
        assertTrue(creators.getBlockCreators().size() == 5);
        assertTrue(creators.getBlockCreators().contains("Bxca7207de79e55c1a69dbc67a4a2e81dfc62c6ac4"));
        assertTrue(creators.getBlockCreators().contains("Bxd8a25ff31c6174ce7bce74ca4a91c2e816dbf91e"));
        assertTrue(creators.getBlockCreators().contains("Bx90865e6e6737fe766dd08f39cc2cf1550b5f3875"));
        assertTrue(creators.getBlockCreators().contains("Bxbb926bbb0b15ca54d4a19dcdf44fc8940e3f6da3"));
        assertTrue(creators.getBlockCreators().contains("Bx8676fb254279ef78c53b8a781e228ab439065786"));
    }

    @Test
    public void testBsrrJoinRatio() throws Exception {
        buildResponse("{\n"
            + "  \"jsonrpc\": \"2.0\",\n"
            + "  \"id\": 1,\n"
            + "  \"result\": 0.13333333333333333\n"
            + "}");
        BSRRJoinRatio joinRatio = deserialiseResponse(BSRRJoinRatio.class);
        assertTrue(joinRatio.getJoinRatio() == 0.13333333333333333D);
    }

    // ========== helpers for tests
    private void assertBigInteger(BigInteger bi, String hex) {
        assertThat(bi.toString(16), is(Numeric.cleanHexPrefix(hex)));
    }

    private long hexToLong(String hex) {
        byte[] bytes = Numeric.hexStringToByteArray(hex);
        return new BigInteger(bytes).longValue();
    }


}
