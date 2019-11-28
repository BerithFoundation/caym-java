package berith.caym.protocol.response;

import java.util.List;
import org.web3j.protocol.core.Response;

/**
 * response of bsrr_getBlockCreatorsByXXX
 */
public class BSRRBlockCreators extends Response<List<String>> {

    public List<String> getBlockCreators() {
        return getResult();
    }
}
