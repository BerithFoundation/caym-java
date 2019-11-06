package berith.caym.protocol.response;

import java.util.List;
import org.web3j.protocol.core.Response;

/**
 * response of amon_getBlockCreatorsByXXX
 */
public class AmonBlockCreators extends Response<List<String>> {

    public List<String> getBlockCreators() {
        return getResult();
    }
}
