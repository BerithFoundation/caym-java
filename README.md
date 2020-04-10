# caym java: Caym Java Berith Dapp API

Welcome to Caym for berith project. The caym helps facilitate Java applications to call remote procedures on Berith network.  

## Fetures  

- Complete implementation of Berith's JSON-RPC client API over HTTP and IPC, WebSocket.

## Getting started  

#### maven

```aidl
will be added
```  

#### gradle  

```aidl
will be added
```  

---  

### Start a berith client with `http`, `ipc`, `websocket` protocol  

> http  

```java
Caym caym = Caym.build(new HttpService("http://localhost:8545"));
```  

> ipc  

```java
Caym caym = Caym.build(new UnixIpcService("/home/app/workspaces/berith/node1/node.ipc"));
```  

> websocket  
  
```java
WebSocketService webSocketService = new WebSocketService("ws://localhost:9545", false);
webSocketService.connect();
Caym caym = Caym.build(webSocketService);
```

> basic usage  

```java
// get latest block with transaction
Block block = caym.berith()
                  .berithGetBlockByNumber(DefaultBlockParameterName.LATEST, true)
                  .send()
                  .getBlock();

// get transaction by hash
Optional<Transaction> transactionOptional = caym.berith()
                                                .berithGetTransactionByHash(
                                                        "0x892a21d9a89afbef98487e3eddebb01b122d36bb899b2fb213a5101bd7b55237")
                                                .send()
                                                .getTransaction();
```  

> batch usage  

```java
BatchResponse response = caym.newBatch()
                         .add(caym.berith().web3ClientVersion())
                         .add(caym.berith().netVersion())
                         .send();

Web3ClientVersion clientVersion = (Web3ClientVersion)response.getResponses().get(0);
NetVersion netVersion = (NetVersion)response.getResponses().get(1);
```  

### Start filters  

> #### new block filter

```java
final Caym caym = Caym.build(new HttpService("http://localhost:8545"));
Callback<String> blockHashCallback = new Callback<String>() {
  @Override
  public void onEvent(String blockHash) {
    try {
      // handle block hash event
      BerithBlock berithBlock = caym.berith().berithGetBlockByHash(blockHash, true).send();
    } catch (IOException e) {
      logger.warn("Exception occur while getting block by hash : " + blockHash);
    }
  }

  @Override
  public void onError(Throwable throwable) {
    logger.warn("Exception occur while polling filter request");
  }
};
BlockFilter blockFilter = new BlockFilter(caym, blockHashCallback);
blockFilter.run(Async.defaultExecutorService(), 5000L);
```  

> #### new pending transaction filter  

```java
final Caym caym = Caym.build(new HttpService("http://localhost:8545"));
Callback<String> txHashCallback = new Callback<String>() {
  @Override
  public void onEvent(String txHash) {
    // handle transaction hash event
  }

  @Override
  public void onError(Throwable throwable) {
    logger.warn("Exception occur while polling filter request");
  }
};

PendingTransactionFilter filter = new PendingTransactionFilter(caym, txHashCallback);
filter.run(Async.defaultExecutorService(), 1000L);
```  

## Sign transaction

> #### sign transaction message by using `ECKeyPair`
>
```java
ECKeyPair keyPair;
long chainId = 106L;
RawTransaction rawTransaction = RawTransaction.createTransferTransaction(
        BigInteger.ZERO, // nonce
        BigInteger.ONE, // gasPrice
        BigInteger.TEN, // gasLimit
        "af304e6259984a857d43406efa1d6528f4055a69", // to
        Convert.toWei(new BigDecimal("0x1"), Unit.ETHER).toBigInteger() // value
);

// Sign transaction message and setted r,s,v in RawTransaction object
Signer.signTransaction(chainId, rawTransaction, keyPair);
String rawTransactionHash = rawTransaction.getRawHash();
String transactionHash = caym.berith()
                             .berithSendRawTransaction(rawTransactionHash)
                             .send()
                             .getTransactionHash();
```  

  


