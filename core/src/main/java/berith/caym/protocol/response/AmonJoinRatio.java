package berith.caym.protocol.response;

import org.web3j.protocol.core.Response;

/**
 * response of amon_getJoinRatio
 */
public class AmonJoinRatio extends Response<Double> {

    public Double getJoinRatio() {
        return getResult();
    }
}
