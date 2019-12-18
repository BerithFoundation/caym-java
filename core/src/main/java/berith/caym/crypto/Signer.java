package berith.caym.crypto;

import java.math.BigInteger;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;

import berith.caym.util.NumericUtil;

/**
 * Signer based on EIP-155 signature data
 */
public final class Signer {

    public static final long BERITH_CHAIN_ID = 106;
    public static final long BERITH_TESTNET_CHAIN_ID = 206;

    private static final int CHAIN_ID_INC = 35;
    private static final int LOWER_REAL_V = 27;

    /**
     * Sign a given RawTransaction with berith main net chain id and key pair
     */
    public static void signTransaction(RawTransaction rawTransaction, ECKeyPair keyPair) {
        signTransaction(BERITH_CHAIN_ID, rawTransaction, keyPair);
    }

    /**
     * Sign a given RawTransaction with chain id and key pair
     */
    public static void signTransaction(long chainId, RawTransaction rawTransaction, ECKeyPair keyPair) {
        final RlpList list = new RlpList(rawTransaction.encodeRlpToSign(chainId));
        final SignatureData sign = Sign.signMessage(RlpEncoder.encode(list), keyPair, true);

        BigInteger v = NumericUtil.toBigInt(sign.getV());

        v = v.subtract(BigInteger.valueOf(LOWER_REAL_V))
             .add(BigInteger.valueOf(chainId * 2))
             .add(BigInteger.valueOf(CHAIN_ID_INC));

        rawTransaction.setV(v);
        rawTransaction.setS(NumericUtil.toBigInt(sign.getS()));
        rawTransaction.setR(NumericUtil.toBigInt(sign.getR()));
    }

    private Signer() {
    }
}


