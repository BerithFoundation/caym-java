package berith.caym.protocol.response;

import java.util.List;
import org.web3j.protocol.core.Response;

/**
 * response of berith_accounts
 */
public class BerithAccounts extends Response<List<String>> {

    public List<String> getAccounts() {
        return getResult();
    }
}
