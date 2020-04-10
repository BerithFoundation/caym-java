package berith.caym;

import org.junit.Test;
import org.web3j.protocol.http.HttpService;

import berith.caym.protocol.BatchTester;

/**
 * Caym test
 */
public class CaymTest extends BatchTester {

    private Caym caym;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        caym = Caym.build(httpService);
    }

    @Test
    public void testNewBatch() throws Exception {
        caym.newBatch()
            .add(caym.berith().web3ClientVersion())
            .add(caym.berith().netVersion())
            .send();

        verifyResult(
                new String[] {
                        "{\"jsonrpc\":\"2.0\",\"method\":\"web3_clientVersion\",\"params\":[],\"id\":1}",
                        "{\"jsonrpc\":\"2.0\",\"method\":\"net_version\",\"params\":[],\"id\":1}"
                });

    }
}
