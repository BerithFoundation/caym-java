package berith.caym;


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
import java.math.BigInteger;
import org.web3j.protocol.admin.methods.response.BooleanResponse;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;

/**
 * Core Berith JSON-RPC API
 */
public interface Berith {

    // tag: web3 module

    /**
     * Returns the current client version {@link Web3ClientVersion}
     */
    Request<?, Web3ClientVersion> web3ClientVersion();

    /**
     * Returns Keccak-256 {@link Web3Sha3}(not the standardized SHA3-256) of the given data
     */
    Request<?, Web3Sha3> web3Sha3(String data);

    // tag: net module

    /**
     * Returns the current network id {@link NetVersion}
     */
    Request<?, NetVersion> netVersion();

    /**
     * Returns number of peers {@link Quantity} currently connected to the client
     */
    Request<?, Quantity> netPeerCount();

    /**
     * Returns true if client is actively listening for network connections
     */
    Request<?, BooleanResponse> netListening();

    // tag: berith module

    /**
     * Returns the current berith protocol version {@link BerithProtocolVersion}
     */
    Request<?, BerithProtocolVersion> berithProtocolVersion();

    /**
     * Returns the client coinbase address {@link BerithCoinbase}
     */
    Request<?, BerithCoinbase> berithCoinbase();

    /**
     * Returns an {@link BerithSyncing.Syncing} with data about the sync status or {@link BerithSyncing.Result}
     */
    Request<?, BerithSyncing> berithSyncing();

    /**
     * Returns a {@link BooleanResponse} with true if client is actively mining new blocks
     */
    Request<?, BooleanResponse> berithMining();

    /**
     * Returns the current price {@link Quantity} per gas in wei.
     */
    Request<?, Quantity> berithGasPrice();

    /**
     * Returns a list of addresses {@link BerithAccounts} owned by client.
     */
    Request<?, BerithAccounts> berithAccounts();

    /**
     * Returns the number {@link Quantity} of most recent block
     */
    Request<?, Quantity> berithBlockNumber();

    /**
     * Returns the balance of the account of given address
     */
    Request<?, Quantity> berithGetBalance(String address, DefaultBlockParameter defaultBlockParameter);

    /**
     * Returns the staked amount of the account of given address
     */
    Request<?, Quantity> berithGetStakeBalance(String address, DefaultBlockParameter defaultBlockParameter);

    /**
     * Returns the number of transactions sent from an address
     */
    Request<?, Quantity> berithGetTransactionCount(String address, DefaultBlockParameter defaultBlockParameter);

    /**
     * Returns the number of transactions in a block from a block matching the given block hash
     */
    Request<?, Quantity> berithGetBlockTransactionCountByHash(String blockHash);

    /**
     * Returns the number of transactions in a block matching the given block number
     */
    Request<?, Quantity> berithGetBlockTransactionCountByNumber(DefaultBlockParameter defaultBlockParameter);

    /**
     * Returns the number of uncles in a block from a block matching the given block hash
     */
    Request<?, Quantity> berithGetUncleCountByBlockHash(String blockHash);

    /**
     * Returns the number of uncles {@link Quantity} in a block from a block matching the given block number
     */
    Request<?, Quantity> berithGetUncleCountByBlockNumber(DefaultBlockParameter defaultBlockParameter);

    /**
     * Returns code at a given address
     */
    Request<?, BerithGetCode> berithGetCode(String address, DefaultBlockParameter defaultBlockParameter);

    /**
     * Returns a signed message {@link BerithSign} sign(keccak256("\x19Berith Signed Message:\n" + len(message) + message)))
     */
    Request<?, BerithSign> berithSign(String address, String sha3HashOfDataToSign);

    /**
     * Creates new message call transaction or a contract creation, if the data field contains code.
     *
     * @return {@link BerithSendTransaction} with transaction hash
     */
    Request<?, BerithSendTransaction> berithSendTransaction(TransactionRequest transactionRequest);

    /**
     * Creates new message call transaction or a contract creation for signed transactions
     *
     * @return {@link BerithSendTransaction} with transaction hash
     */
    Request<?, BerithSendTransaction> berithSendRawTransaction(String rawTransaction);

    /**
     * Executes a new message call immediately without creating a transaction on the block chain
     */
    Request<?, BerithCall> berithCall(TransactionRequest transactionRequest, DefaultBlockParameter defaultBlockParameter);

    /**
     * Returns an estimated of how much gas is necessary to allow the transaction to complete
     */
    Request<?, Quantity> berithEstimateGas(TransactionRequest transactionRequest);

    /**
     * Returns a {@link BerithBlock} information given block hash
     */
    Request<?, BerithBlock> berithGetBlockByHash(String blockHash, boolean fullTransactionObjects);

    /**
     * Returns a {@link BerithBlock} information given block parameter
     */
    Request<?, BerithBlock> berithGetBlockByNumber(DefaultBlockParameter defaultBlockParameter, boolean fullTransactionObjects);

    /**
     * Returns a {@link BerithTransaction} information given transaction hash
     */
    Request<?, BerithTransaction> berithGetTransactionByHash(String transactionHash);

    /**
     * Returns a {@link BerithTransaction} information given block hash and transaction index position
     */
    Request<?, BerithTransaction> berithGetTransactionByBlockHashAndIndex(String blockHash, BigInteger transactionIndex);

    /**
     * Returns a {@link BerithTransaction} information given block number and transaction index position
     */
    Request<?, BerithTransaction> berithGetTransactionByBlockNumberAndIndex(DefaultBlockParameter defaultBlockParameter,
        BigInteger transactionIndex);

    /**
     * Returns a {@link BerithGetTransactionReceipt} given transaction hash
     */
    Request<?, BerithGetTransactionReceipt> berithGetTransactionReceipt(String transactionHash);

    /**
     * Creates a filter object based on filter options.
     */
    Request<?, BerithFilter> berithNewFilter(BerithFilterRequest berithFilter);

    /**
     * Creates a filter to notify when a new block arrives
     */
    Request<?, BerithFilter> berithNewBlockFilter();

    /**
     * Creates a filter to notify when new pending transactions arrive
     */
    Request<?, BerithFilter> berithNewPendingTransactionFilter();

    /**
     * Uninstalls a filter with given id
     */
    Request<?, BerithUninstallFilter> berithUninstallFilter(BigInteger filterId);

    /**
     * Polling method for a filter
     */
    Request<?, BerithLog> berithGetFilterChanges(BigInteger filterId);

    /**
     * Returns an array of all logs
     */
    Request<?, BerithLog> berithGetFilterLogs(BigInteger filterId);

    /**
     * Returns an array of all logs
     */
    Request<?, BerithLog> berithGetLogs(BerithFilterRequest berithFilterRequest);

    // tag: bsrr module

    /**
     * Returns an array of addresses {@link BSRRBlockCreators} who can seal a block given block number
     */
    Request<?, BSRRBlockCreators> bsrrGetBlockCreatorsByNumber(DefaultBlockParameter defaultBlockParameter);

    /**
     * Returns an array of addresses {@link BSRRBlockCreators} who can seal a block given block hash
     */
    Request<?, BSRRBlockCreators> bsrrGetBlockCreatorsByHash(String blockHash);

    /**
     * Returns a probability of the top of block creators
     */
    Request<?, BSRRJoinRatio> bsrrGetJoinRatio(String address, DefaultBlockParameter defaultBlockParameter);
}
