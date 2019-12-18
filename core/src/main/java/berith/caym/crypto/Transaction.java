package berith.caym.crypto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Bytes;
import org.web3j.utils.Numeric;

public class Transaction {
	
	class TxData {
		private BigInteger nonce;
	    private BigInteger gasPrice;
	    private BigInteger gasLimit;
	    private String to;
	    private BigInteger value;
	    private String data;
	    private int base;
	    private int target;
	    
	    private TxData(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value,
				String data,int base, int target) {
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
	    	List<RlpType> result = new ArrayList<>();
	    	result.add(RlpString.create(nonce));
	        result.add(RlpString.create(gasPrice));
	        result.add(RlpString.create(gasLimit));

	        if (to != null && to.length() > 0) {
	            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
	        } else {
	            result.add(RlpString.create(""));
	        }

	        result.add(RlpString.create(value));

	        byte[] data = Numeric.hexStringToByteArray(this.data);
	        result.add(RlpString.create(data));
	        
	        result.add(RlpString.create(base));
	        result.add(RlpString.create(target));
	        
	        return result;
	    }

		
	}
	
	private TxData txdata;
	private BigInteger v;
	private BigInteger r;
	private BigInteger s;

	public List<RlpType> dataToRlp() {
		return txdata.asRlp();
	}
	
	public Transaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value,
			String data, int base, int target) {
		this.txdata = new TxData(nonce,gasPrice,gasLimit,to,value,data,base,target);
	}
	
	public Transaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value,
			String data, int base, int target, BigInteger v, BigInteger r, BigInteger s) {
		this.txdata = new TxData(nonce,gasPrice,gasLimit,to,value,data,base,target);
		this.v = v;
		this.r = r;
		this.s = s;
	}
	
	public List<RlpType> encodeRlpToSign(long chainID) {
		List<RlpType> result = txdata.asRlp();
		result.add(RlpString.create(chainID));
		result.add(RlpString.create(0));
		result.add(RlpString.create(0));
        return result;
	}
	
	public List<RlpType> encodeRLP() {
    	List<RlpType> result = txdata.asRlp();
    	result.add(RlpString.create(v));
    	result.add(RlpString.create(r));
    	result.add(RlpString.create(s));
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
	
	
	
	
}

