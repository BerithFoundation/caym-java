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

## Start a berith client  

> http  

```aidl
Caym caym = Caym.build(new HttpService("http://localhost:8545"));
```  

> ipc  

```aidl
Caym caym = Caym.build(new UnixIpcService("/home/app/workspaces/berith/node1/node.ipc"));
```  

> websocket  
  
```aidl
WebSocketService webSocketService = new WebSocketService("ws://localhost:9545", false);
webSocketService.connect();
Caym caym = Caym.build(webSocketService);
```

## Start filters  

> ### new block filter

```aidl
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

> ### new pending transaction filter  

```aidl
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
