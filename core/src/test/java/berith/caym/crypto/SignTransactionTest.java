package berith.caym.crypto;

import static org.hamcrest.CoreMatchers.is;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.web3j.crypto.ECKeyPair;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;

import berith.caym.util.NumericUtil;

public class SignTransactionTest {

    @Test
    public void testSignTx() {
        RawTransaction tx = new RawTransaction(
                BigInteger.valueOf(1),
                BigInteger.valueOf(1000000000l),
                BigInteger.valueOf(21000),
                "1234567890123456789012345678901234567890",
                BigInteger.valueOf(1234567890l),
                "",
                1,
                1
        );
        String expectedResult =
                "0xf86a01843b9aca0082520894123456789012345678901234567890123456789084499602d280010181f8a079bd31403163eecc3badbe97013ba313b11414273cc757730fa30af8311e5b9ba05f8878cf0661ff3d5c0309facc3c501caf8a291ab1800b7cbc9ef7b8973d5776";
        ECKeyPair keyPair = ECKeyPair.create(BigInteger.valueOf(3360));

        Signer.signTransaction(Signer.BERITH_CHAIN_ID, tx, keyPair);
        String result = NumericUtil.toHexString(RlpEncoder.encode(new RlpList(tx.encodeRLP())));

        Assert.assertThat(result, is(expectedResult));
    }
}
