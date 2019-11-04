package berith.caym;

import org.web3j.protocol.Web3jService;

public interface Caym {

    /**
     * default http url
     */
    String DEFAULT_HTTP_URL = "http://localhost:8545";
    /**
     * berith main network's block time
     */
    int DEFAULT_BLOCK_TIME = 5;

    /**
     * Create a Caym instance given {@link Web3jService}
     *
     * @return new {@link Caym} instance
     */
    static Caym build(Web3jService web3jService) {
        return new CaymImpl(web3jService);
    }

    /**
     * Returns a {@link Berith} instance
     */
    Berith berith();

    /**
     * Returns a {@link Web3jService} in {@link Caym} instance
     */
    Web3jService getWeb3JService();
}
