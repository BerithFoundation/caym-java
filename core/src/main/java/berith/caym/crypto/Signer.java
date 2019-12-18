package berith.caym.crypto;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Numeric;

public class Signer {

	private long chainID;

	public static long BERITH_CHAIN_ID = 106;
	public static long BERITH_TESTNET_CHAIN_ID = 206;
	
	private static final int CHAIN_ID_INC = 35;
    private static final int LOWER_REAL_V = 27;
	
	public Signer(long chainID) {
		this.chainID = chainID;
	}
	
	public void signTx(Transaction tx, ECKeyPair keyPair) {
		RlpList list = new RlpList(tx.encodeRlpToSign(chainID));
		SignatureData sign = Sign.signMessage(RlpEncoder.encode(list), keyPair, true);
		
		BigInteger v = Numeric.toBigInt(sign.getV());
		v = v.subtract(BigInteger.valueOf(LOWER_REAL_V));
        v = v.add(BigInteger.valueOf(chainID * 2));
        v = v.add(BigInteger.valueOf(CHAIN_ID_INC));
        
        tx.setV(v);
		tx.setS(Numeric.toBigInt(sign.getS()));
		tx.setR(Numeric.toBigInt(sign.getR()));
	}
}


