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

package berith.caym.protocol.response;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.Response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import berith.caym.protocol.response.BerithBlock.Block;
import berith.caym.util.NumericUtil;

/**
 * Berith block object
 */
public class BerithBlock extends Response<Block> {

    @Override
    @JsonDeserialize(using = ResponseDeserializer.class)
    public void setResult(Block result) {
        super.setResult(result);
    }

    public Block getBlock() {
        return getResult();
    }

    public static class Block {

        /**
         * the block number
         */
        private String number;
        /**
         * hash of the block
         */
        private String hash;
        /**
         * hash of the parent block
         */
        private String parentHash;
        /**
         * block creator's rank of stake holders
         */
        private String nonce;
        /**
         * SHA3 of the uncles data in the block
         */
        private String sha3Uncles;
        /**
         * the bloom filter for the logs of the block
         */
        private String logsBloom;
        /**
         * the root of the transaction trie of the block
         */
        private String transactionsRoot;
        /**
         * the root of the receipts trie of the block
         */
        private String stateRoot;
        /**
         * the root of the receipts trie of the block
         */
        private String receiptsRoot;
        /**
         * block miner
         */
        private String miner;

        private String mixHash;
        /**
         * the difficulty for this block
         */
        private String difficulty;
        /**
         * the total difficulty of the chain until this block
         */
        private String totalDifficulty;
        /**
         * extra data field of this block
         */
        private String extraData;
        /**
         * size of this block in bytes
         */
        private String size;
        /**
         * maximum gas allowed in this block
         */
        private String gasLimit;
        /**
         * total used gas by all transactions in this block
         */
        private String gasUsed;
        /**
         * the unix timestamp
         */
        private String timestamp;
        private List<TransactionResult> transactions;
        /**
         * uncle hashes
         */
        private List<String> uncles;
        /**
         *
         */
        private List<String> sealFields;

        public Block() {
        }

        public Block(String number, String hash, String parentHash, String nonce, String sha3Uncles, String logsBloom,
            String transactionsRoot, String stateRoot, String receiptsRoot, String miner, String mixHash, String difficulty,
            String totalDifficulty, String extraData, String size, String gasLimit, String gasUsed, String timestamp,
            List<TransactionResult> transactions, List<String> uncles, List<String> sealFields) {

            this.number = number;
            this.hash = hash;
            this.parentHash = parentHash;
            this.nonce = nonce;
            this.sha3Uncles = sha3Uncles;
            this.logsBloom = logsBloom;
            this.transactionsRoot = transactionsRoot;
            this.stateRoot = stateRoot;
            this.receiptsRoot = receiptsRoot;
            this.miner = miner;
            this.mixHash = mixHash;
            this.difficulty = difficulty;
            this.totalDifficulty = totalDifficulty;
            this.extraData = extraData;
            this.size = size;
            this.gasLimit = gasLimit;
            this.gasUsed = gasUsed;
            this.timestamp = timestamp;
            this.transactions = transactions;
            this.uncles = uncles;
            this.sealFields = sealFields;
        }

        public BigInteger getNumber() {
            return NumericUtil.decodeQuantity(number);
        }

        public String getNumberRaw() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getParentHash() {
            return parentHash;
        }

        public void setParentHash(String parentHash) {
            this.parentHash = parentHash;
        }

        public BigInteger getNonce() {
            return NumericUtil.decodeQuantity(nonce);
        }

        public String getNonceRaw() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getSha3Uncles() {
            return sha3Uncles;
        }

        public void setSha3Uncles(String sha3Uncles) {
            this.sha3Uncles = sha3Uncles;
        }

        public String getLogsBloom() {
            return logsBloom;
        }

        public void setLogsBloom(String logsBloom) {
            this.logsBloom = logsBloom;
        }

        public String getTransactionsRoot() {
            return transactionsRoot;
        }

        public void setTransactionsRoot(String transactionsRoot) {
            this.transactionsRoot = transactionsRoot;
        }

        public String getStateRoot() {
            return stateRoot;
        }

        public void setStateRoot(String stateRoot) {
            this.stateRoot = stateRoot;
        }

        public String getReceiptsRoot() {
            return receiptsRoot;
        }

        public void setReceiptsRoot(String receiptsRoot) {
            this.receiptsRoot = receiptsRoot;
        }

        public String getMiner() {
            return miner;
        }

        public void setMiner(String miner) {
            this.miner = miner;
        }

        public String getMixHash() {
            return mixHash;
        }

        public void setMixHash(String mixHash) {
            this.mixHash = mixHash;
        }

        public BigInteger getDifficulty() {
            return NumericUtil.decodeQuantity(difficulty);
        }

        public String getDifficultyRaw() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }

        public BigInteger getTotalDifficulty() {
            return NumericUtil.decodeQuantity(totalDifficulty);
        }

        public String getTotalDifficultyRaw() {
            return totalDifficulty;
        }

        public void setTotalDifficulty(String totalDifficulty) {
            this.totalDifficulty = totalDifficulty;
        }

        public String getExtraData() {
            return extraData;
        }

        public void setExtraData(String extraData) {
            this.extraData = extraData;
        }

        public BigInteger getSize() {
            return NumericUtil.decodeQuantity(size);
        }

        public String getSizeRaw() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public BigInteger getGasLimit() {
            return NumericUtil.decodeQuantity(gasLimit);
        }

        public String getGasLimitRaw() {
            return gasLimit;
        }

        public void setGasLimit(String gasLimit) {
            this.gasLimit = gasLimit;
        }

        public BigInteger getGasUsed() {
            return NumericUtil.decodeQuantity(gasUsed);
        }

        public String getGasUsedRaw() {
            return gasUsed;
        }

        public void setGasUsed(String gasUsed) {
            this.gasUsed = gasUsed;
        }

        public BigInteger getTimestamp() {
            return NumericUtil.decodeQuantity(timestamp);
        }

        public String getTimestampRaw() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public List<TransactionResult> getTransactions() {
            return transactions;
        }

        @JsonDeserialize(using = ResultTransactionDeserializer.class)
        public void setTransactions(List<TransactionResult> transactions) {
            this.transactions = transactions;
        }

        public List<String> getUncles() {
            return uncles;
        }

        public void setUncles(List<String> uncles) {
            this.uncles = uncles;
        }

        public List<String> getSealFields() {
            return sealFields;
        }

        public void setSealFields(List<String> sealFields) {
            this.sealFields = sealFields;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Block)) {
                return false;
            }

            Block block = (Block) o;

            if (getNumberRaw() != null
                ? !getNumberRaw().equals(block.getNumberRaw()) : block.getNumberRaw() != null) {
                return false;
            }
            if (getHash() != null ? !getHash().equals(block.getHash()) : block.getHash() != null) {
                return false;
            }
            if (getParentHash() != null
                ? !getParentHash().equals(block.getParentHash())
                : block.getParentHash() != null) {
                return false;
            }
            if (getNonceRaw() != null
                ? !getNonceRaw().equals(block.getNonceRaw()) : block.getNonceRaw() != null) {
                return false;
            }
            if (getSha3Uncles() != null
                ? !getSha3Uncles().equals(block.getSha3Uncles())
                : block.getSha3Uncles() != null) {
                return false;
            }
            if (getLogsBloom() != null
                ? !getLogsBloom().equals(block.getLogsBloom())
                : block.getLogsBloom() != null) {
                return false;
            }
            if (getTransactionsRoot() != null
                ? !getTransactionsRoot().equals(block.getTransactionsRoot())
                : block.getTransactionsRoot() != null) {
                return false;
            }
            if (getStateRoot() != null
                ? !getStateRoot().equals(block.getStateRoot())
                : block.getStateRoot() != null) {
                return false;
            }
            if (getReceiptsRoot() != null
                ? !getReceiptsRoot().equals(block.getReceiptsRoot())
                : block.getReceiptsRoot() != null) {
                return false;
            }
            if (getMiner() != null
                ? !getMiner().equals(block.getMiner()) : block.getMiner() != null) {
                return false;
            }
            if (getMixHash() != null
                ? !getMixHash().equals(block.getMixHash()) : block.getMixHash() != null) {
                return false;
            }
            if (getDifficultyRaw() != null
                ? !getDifficultyRaw().equals(block.getDifficultyRaw())
                : block.getDifficultyRaw() != null) {
                return false;
            }
            if (getTotalDifficultyRaw() != null
                ? !getTotalDifficultyRaw().equals(block.getTotalDifficultyRaw())
                : block.getTotalDifficultyRaw() != null) {
                return false;
            }
            if (getExtraData() != null
                ? !getExtraData().equals(block.getExtraData())
                : block.getExtraData() != null) {
                return false;
            }
            if (getSizeRaw() != null
                ? !getSizeRaw().equals(block.getSizeRaw())
                : block.getSizeRaw() != null) {
                return false;
            }
            if (getGasLimitRaw() != null
                ? !getGasLimitRaw().equals(block.getGasLimitRaw())
                : block.getGasLimitRaw() != null) {
                return false;
            }
            if (getGasUsedRaw() != null
                ? !getGasUsedRaw().equals(block.getGasUsedRaw())
                : block.getGasUsedRaw() != null) {
                return false;
            }
            if (getTimestampRaw() != null
                ? !getTimestampRaw().equals(block.getTimestampRaw())
                : block.getTimestampRaw() != null) {
                return false;
            }
            if (getTransactions() != null
                ? !getTransactions().equals(block.getTransactions())
                : block.getTransactions() != null) {
                return false;
            }
            if (getUncles() != null
                ? !getUncles().equals(block.getUncles()) : block.getUncles() != null) {
                return false;
            }
            return getSealFields() != null
                ? getSealFields().equals(block.getSealFields()) : block.getSealFields() == null;
        }

        @Override
        public int hashCode() {
            int result = getNumberRaw() != null ? getNumberRaw().hashCode() : 0;
            result = 31 * result + (getHash() != null ? getHash().hashCode() : 0);
            result = 31 * result + (getParentHash() != null ? getParentHash().hashCode() : 0);
            result = 31 * result + (getNonceRaw() != null ? getNonceRaw().hashCode() : 0);
            result = 31 * result + (getSha3Uncles() != null ? getSha3Uncles().hashCode() : 0);
            result = 31 * result + (getLogsBloom() != null ? getLogsBloom().hashCode() : 0);
            result = 31 * result
                + (getTransactionsRoot() != null ? getTransactionsRoot().hashCode() : 0);
            result = 31 * result + (getStateRoot() != null ? getStateRoot().hashCode() : 0);
            result = 31 * result + (getReceiptsRoot() != null ? getReceiptsRoot().hashCode() : 0);
            result = 31 * result + (getMiner() != null ? getMiner().hashCode() : 0);
            result = 31 * result + (getMixHash() != null ? getMixHash().hashCode() : 0);
            result = 31 * result + (getDifficultyRaw() != null ? getDifficultyRaw().hashCode() : 0);
            result = 31 * result
                + (getTotalDifficultyRaw() != null ? getTotalDifficultyRaw().hashCode() : 0);
            result = 31 * result + (getExtraData() != null ? getExtraData().hashCode() : 0);
            result = 31 * result + (getSizeRaw() != null ? getSizeRaw().hashCode() : 0);
            result = 31 * result + (getGasLimitRaw() != null ? getGasLimitRaw().hashCode() : 0);
            result = 31 * result + (getGasUsedRaw() != null ? getGasUsedRaw().hashCode() : 0);
            result = 31 * result + (getTimestampRaw() != null ? getTimestampRaw().hashCode() : 0);
            result = 31 * result + (getTransactions() != null ? getTransactions().hashCode() : 0);
            result = 31 * result + (getUncles() != null ? getUncles().hashCode() : 0);
            result = 31 * result + (getSealFields() != null ? getSealFields().hashCode() : 0);
            return result;
        }
    }


    @FunctionalInterface
    public interface TransactionResult<T> {

        T get();
    }

    public static class TransactionHash implements TransactionResult<String> {

        private String value;

        public TransactionHash() {
        }

        public TransactionHash(String value) {
            this.value = value;
        }

        @Override
        public String get() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TransactionHash)) {
                return false;
            }

            TransactionHash that = (TransactionHash) o;

            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }

    public static class TransactionObject extends Transaction implements TransactionResult<Transaction> {

        public TransactionObject() {
        }

        public TransactionObject(String hash, String nonce, String blockHash, String blockNumber,
            String transactionIndex, String from, String to, String value,
            String gasPrice, String gas, String input, String r, String s, int v,
            Transaction.Type base, Transaction.Type target, String creates, String publicKey, String raw) {

            super(hash, nonce, blockHash, blockNumber, transactionIndex, from, to, value,
                gasPrice, gas, input, r, s, v, base, target, creates, publicKey, raw);
        }

        @Override
        public Transaction get() {
            return this;
        }
    }

    public static class ResultTransactionDeserializer extends JsonDeserializer<List<TransactionResult>> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public List<TransactionResult> deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {

            List<TransactionResult> transactionResults = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<TransactionObject> transactionObjectIterator = objectReader.readValues(jsonParser, TransactionObject.class);
                while (transactionObjectIterator.hasNext()) {
                    transactionResults.add(transactionObjectIterator.next());
                }
            } else if (nextToken == JsonToken.VALUE_STRING) {
                jsonParser.getValueAsString();

                Iterator<TransactionHash> transactionHashIterator = objectReader.readValues(jsonParser, TransactionHash.class);
                while (transactionHashIterator.hasNext()) {
                    transactionResults.add(transactionHashIterator.next());
                }
            }

            return transactionResults;
        }
    }

    public static class ResponseDeserializer extends JsonDeserializer<Block> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public Block deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, Block.class);
            } else {
                return null;  // null is wrapped by Optional in above getter
            }
        }
    }
}
