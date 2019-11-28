package berith.caym.protocol.response;

import org.web3j.protocol.core.Response;

/**
 * response of amon_getJoinRatio
 */
public class BSRRJoinRatio extends Response<Double> {

    public Double getJoinRatio() {
        return getResult();
    }
}
