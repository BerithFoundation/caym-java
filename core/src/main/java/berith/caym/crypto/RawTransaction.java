package berith.caym.crypto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;

import berith.caym.util.NumericUtil;

/**
 * Berith raw transaction
 */
public class RawTransaction {

    private TxData txdata;
    private BigInteger v;
    private BigInteger r;
    private BigInteger s;

    /** Raw transaction hash */
    private String rawHash;

    /**
     * Create a transfer (Main to Main) {@link RawTransaction}
     */
    public static RawTransaction createTransferTransaction(BigInteger nonce, BigInteger gasPrice,
                                                           BigInteger gasLimit, String to, BigInteger value) {

        return createBerithTransaction(nonce, gasPrice, gasLimit, to, value, Type.MAIN, Type.MAIN);
    }

    /**
     * Create a stake (Main to Stake) {@link RawTransaction}
     */
    public static RawTransaction createStakeTransaction(BigInteger nonce, BigInteger gasPrice,
                                                        BigInteger gasLimit, String to, BigInteger value) {

        return createBerithTransaction(nonce, gasPrice, gasLimit, to, value, Type.MAIN, Type.STAKE);
    }

    /**
     * Create a un stake (Stake to Main) {@link RawTransaction}
     */
    public static RawTransaction createUnStakeTransaction(BigInteger nonce, BigInteger gasPrice,
                                                          BigInteger gasLimit, String to) {

        return createBerithTransaction(nonce, gasPrice, gasLimit, to, BigInteger.ZERO, Type.STAKE, Type.MAIN);
    }

    /**
     * Create a {@link RawTransaction} given transfer params
     */
    public static RawTransaction createBerithTransaction(BigInteger nonce, BigInteger gasPrice,
                                                         BigInteger gasLimit, String to, BigInteger value,
                                                         Type base, Type target) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, "", base.getValue(), target.getValue());
    }

    /**
     * Create a contract creation {@link RawTransaction}
     */
    public static RawTransaction createContractTransaction(BigInteger nonce, BigInteger gasPrice,
                                                           BigInteger gasLimit, BigInteger value, String init) {

        return new RawTransaction(nonce, gasPrice, gasLimit, null,
                                  value, init, Type.MAIN.getValue(), Type.MAIN.getValue());
    }

    /**
     * Create a contract function call {@link RawTransaction}
     */
    public static RawTransaction createFunctionCallTransaction(BigInteger nonce, BigInteger gasPrice,
                                                               BigInteger gasLimit, String to,
                                                               BigInteger value, String init) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, init,
                                  Type.MAIN.getValue(), Type.MAIN.getValue());
    }

    /**
     * Create a {@link RawTransaction} from raw transaction hash
     */
    public static RawTransaction fromRawHash(String rawHash) {
        final byte[] rlpEncoded = NumericUtil.hexStringToByteArray(rawHash);
        final RlpList rlpList = RlpDecoder.decode(rlpEncoded);

        final RlpList values = (RlpList) rlpList.getValues().get(0);
        final BigInteger nonce = ((RlpString) values.getValues().get(0)).asPositiveBigInteger();
        final BigInteger gasPrice = ((RlpString) values.getValues().get(1)).asPositiveBigInteger();
        final BigInteger gasLimit = ((RlpString) values.getValues().get(2)).asPositiveBigInteger();
        //final String to = ((RlpString) values.getValues().get(3)).asString();
        final String to = ((RlpString) values.getValues().get(3)).asPositiveBigInteger().toString(16);
        final BigInteger value = ((RlpString) values.getValues().get(4)).asPositiveBigInteger();
        final String data = ((RlpString) values.getValues().get(5)).asString();
        final int base = ((RlpString) values.getValues().get(6)).asPositiveBigInteger().intValue();
        final int target = ((RlpString) values.getValues().get(7)).asPositiveBigInteger().intValue();

        if (values.getValues().size() > 8 && values.getValues().get(9) instanceof RlpString) {
            final BigInteger v = NumericUtil.toBigInt(((RlpString) values.getValues().get(8)).getBytes());
            final BigInteger r = NumericUtil.toBigInt(((RlpString) values.getValues().get(9)).getBytes());
            final BigInteger s = NumericUtil.toBigInt(((RlpString) values.getValues().get(10)).getBytes());

            return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, base, target,
                                      v, r, s);
        } else {
            return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, base, target);
        }
    }

    /**
     * Constructor with no signature data
     */
    public RawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                          BigInteger value, String data, int base, int target) {

        txdata = new TxData(nonce, gasPrice, gasLimit, to, value, data, base, target);
    }

    /**
     * Constructor with signature data
     */
    public RawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                          BigInteger value,
                          String data, int base, int target, BigInteger v, BigInteger r, BigInteger s) {

        txdata = new TxData(nonce, gasPrice, gasLimit, to, value, data, base, target);

        this.v = v;
        this.r = r;
        this.s = s;
    }

    /**
     * Returns a raw transaction hash
     */
    public synchronized String getRawHash() {
        if (rawHash != null) {
            return rawHash;
        }

        return rawHash = NumericUtil.toHexString(RlpEncoder.encode(new RlpList(encodeRLP())));
    }

    public List<RlpType> dataToRlp() {
        return txdata.asRlp();
    }

    public List<RlpType> encodeRlpToSign(long chainID) {
        final List<RlpType> result = txdata.asRlp();

        result.add(RlpString.create(chainID));
        result.add(RlpString.create(0));
        result.add(RlpString.create(0));

        return result;
    }

    public List<RlpType> encodeRLP() {
        final List<RlpType> result = txdata.asRlp();

        if (v != null && r != null && s != null) {
            result.add(RlpString.create(v));
            result.add(RlpString.create(r));
            result.add(RlpString.create(s));
        }

        return result;
    }

    public BigInteger getNonce() {
        return txdata.nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.txdata.nonce = nonce;
    }

    public BigInteger getGasPrice() {
        return txdata.gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.txdata.gasPrice = gasPrice;
    }

    public BigInteger getGasLimit() {
        return txdata.gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.txdata.gasLimit = gasLimit;
    }

    public String getTo() {
        return txdata.to;
    }

    public void setTo(String to) {
        this.txdata.to = to;
    }

    public BigInteger getValue() {
        return txdata.value;
    }

    public void setValue(BigInteger value) {
        this.txdata.value = value;
    }

    public String getData() {
        return txdata.data;
    }

    public void setData(String data) {
        this.txdata.data = data;
    }

    public int getBase() {
        return txdata.base;
    }

    public void setBase(int base) {
        this.txdata.base = base;
    }

    public int getTarget() {
        return txdata.target;
    }

    public void setTarget(int target) {
        this.txdata.target = target;
    }

    public TxData getTxdata() {
        return txdata;
    }

    public void setTxdata(TxData txdata) {
        this.txdata = txdata;
    }

    public BigInteger getV() {
        return v;
    }

    public void setV(BigInteger v) {
        this.v = v;
    }

    public BigInteger getR() {
        return r;
    }

    public void setR(BigInteger r) {
        this.r = r;
    }

    public BigInteger getS() {
        return s;
    }

    public void setS(BigInteger s) {
        this.s = s;
    }

    public enum Type {
        MAIN(1), STAKE(2);

        private int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Type getType(int value) {
            switch (value) {
                case 1:
                    return MAIN;
                case 2:
                    return STAKE;
            }

            return null;
        }
    }

    private static final class TxData {

        private BigInteger nonce;
        private BigInteger gasPrice;
        private BigInteger gasLimit;
        private String to;
        private BigInteger value;
        private String data;
        private int base;
        private int target;

        private TxData(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit,
                       String to, BigInteger value, String data, int base, int target) {

            this.nonce = nonce;
            this.gasPrice = gasPrice;
            this.gasLimit = gasLimit;
            this.to = to;
            this.value = value;
            this.data = data;
            this.base = base;
            this.target = target;
        }

        private List<RlpType> asRlp() {
            final List<RlpType> result = new ArrayList<>();

            result.add(RlpString.create(nonce));
            result.add(RlpString.create(gasPrice));
            result.add(RlpString.create(gasLimit));

            if (to != null && !to.isEmpty()) {
                result.add(RlpString.create(
                        NumericUtil.hexStringToByteArray(NumericUtil.cleanAddressPrefix(to))));
            } else {
                result.add(RlpString.create(""));
            }

            result.add(RlpString.create(value));

            final byte[] data = NumericUtil.hexStringToByteArray(this.data);
            result.add(RlpString.create(data));

            result.add(RlpString.create(base));
            result.add(RlpString.create(target));

            return result;
        }
    }
}
