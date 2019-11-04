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

import java.math.BigInteger;
import java.util.List;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.utils.Numeric;

public class TransactionReceipt {

    /**
     * hash of the transaction
     */
    private String transactionHash;
    /**
     * this transaction's index position in the block
     */
    private String transactionIndex;
    /**
     * hash of the block
     */
    private String blockHash;
    /**
     * number of the block
     */
    private String blockNumber;
    /**
     * total amount of the receiver
     */
    private String cumulativeGasUsed;
    /**
     * amount of gas used
     */
    private String gasUsed;
    /**
     * contract address created if this transaction was a contract creation
     */
    private String contractAddress;
    /**
     * status of execution transaction
     */
    private String status;
    /**
     * address of the sender
     */
    private String from;
    /**
     * address of the receiver
     */
    private String to;
    /**
     * logs which this transaction generated
     */
    private List<Log> logs;
    /**
     * bloom filter for light clients
     */
    private String logsBloom;

    public TransactionReceipt() {
    }

    public TransactionReceipt(String transactionHash, String transactionIndex, String blockHash, String blockNumber,
        String cumulativeGasUsed, String gasUsed, String contractAddress, String status, String from, String to,
        List<Log> logs, String logsBloom) {

        this.transactionHash = transactionHash;
        this.transactionIndex = transactionIndex;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.cumulativeGasUsed = cumulativeGasUsed;
        this.gasUsed = gasUsed;
        this.contractAddress = contractAddress;
        this.status = status;
        this.from = from;
        this.to = to;
        this.logs = logs;
        this.logsBloom = logsBloom;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public BigInteger getTransactionIndex() {
        return Numeric.decodeQuantity(transactionIndex);
    }

    public String getTransactionIndexRaw() {
        return transactionIndex;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(blockNumber);
    }

    public String getBlockNumberRaw() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigInteger getCumulativeGasUsed() {
        return Numeric.decodeQuantity(cumulativeGasUsed);
    }

    public String getCumulativeGasUsedRaw() {
        return cumulativeGasUsed;
    }

    public void setCumulativeGasUsed(String cumulativeGasUsed) {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }

    public BigInteger getGasUsed() {
        return Numeric.decodeQuantity(gasUsed);
    }

    public String getGasUsedRaw() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isStatusOK() {
        if (null == status) {
            return true;
        }
        BigInteger statusQuantity = Numeric.decodeQuantity(status);
        return BigInteger.ONE.equals(statusQuantity);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom) {
        this.logsBloom = logsBloom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionReceipt)) {
            return false;
        }

        TransactionReceipt that = (TransactionReceipt) o;

        if (getTransactionHash() != null
            ? !getTransactionHash().equals(that.getTransactionHash())
            : that.getTransactionHash() != null) {
            return false;
        }
        if (transactionIndex != null
            ? !transactionIndex.equals(that.transactionIndex)
            : that.transactionIndex != null) {
            return false;
        }
        if (getBlockHash() != null
            ? !getBlockHash().equals(that.getBlockHash())
            : that.getBlockHash() != null) {
            return false;
        }
        if (blockNumber != null
            ? !blockNumber.equals(that.blockNumber) : that.blockNumber != null) {
            return false;
        }
        if (cumulativeGasUsed != null
            ? !cumulativeGasUsed.equals(that.cumulativeGasUsed)
            : that.cumulativeGasUsed != null) {
            return false;
        }
        if (gasUsed != null ? !gasUsed.equals(that.gasUsed) : that.gasUsed != null) {
            return false;
        }
        if (getContractAddress() != null
            ? !getContractAddress().equals(that.getContractAddress())
            : that.getContractAddress() != null) {
            return false;
        }
        if (getStatus() != null
            ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) {
            return false;
        }
        if (getFrom() != null ? !getFrom().equals(that.getFrom()) : that.getFrom() != null) {
            return false;
        }
        if (getTo() != null ? !getTo().equals(that.getTo()) : that.getTo() != null) {
            return false;
        }
        if (getLogs() != null ? !getLogs().equals(that.getLogs()) : that.getLogs() != null) {
            return false;
        }
        return getLogsBloom() != null
            ? getLogsBloom().equals(that.getLogsBloom()) : that.getLogsBloom() == null;
    }

    @Override
    public int hashCode() {
        int result = getTransactionHash() != null ? getTransactionHash().hashCode() : 0;
        result = 31 * result + (transactionIndex != null ? transactionIndex.hashCode() : 0);
        result = 31 * result + (getBlockHash() != null ? getBlockHash().hashCode() : 0);
        result = 31 * result + (blockNumber != null ? blockNumber.hashCode() : 0);
        result = 31 * result + (cumulativeGasUsed != null ? cumulativeGasUsed.hashCode() : 0);
        result = 31 * result + (gasUsed != null ? gasUsed.hashCode() : 0);
        result = 31 * result + (getContractAddress() != null ? getContractAddress().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getFrom() != null ? getFrom().hashCode() : 0);
        result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
        result = 31 * result + (getLogs() != null ? getLogs().hashCode() : 0);
        result = 31 * result + (getLogsBloom() != null ? getLogsBloom().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransactionReceipt{"
            + "transactionHash='" + transactionHash + '\''
            + ", transactionIndex='" + transactionIndex + '\''
            + ", blockHash='" + blockHash + '\''
            + ", blockNumber='" + blockNumber + '\''
            + ", cumulativeGasUsed='" + cumulativeGasUsed + '\''
            + ", gasUsed='" + gasUsed + '\''
            + ", contractAddress='" + contractAddress + '\''
            + ", status='" + status + '\''
            + ", from='" + from + '\''
            + ", to='" + to + '\''
            + ", logs=" + logs
            + ", logsBloom='" + logsBloom + '\''
            + '}';
    }
}
