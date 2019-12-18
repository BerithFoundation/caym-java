package berith.caym.crypto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.Test;
import org.web3j.crypto.ECKeyPair;

import berith.caym.crypto.RawTransaction.Type;

public class RawTransactionTest {

    @Test
    public void testDecodeRawTransactionWithoutSignature() {
        // given
        BigInteger nonce = BigInteger.ZERO;
        BigInteger gasPrice = BigInteger.ONE;
        BigInteger gasLimit = BigInteger.TEN;
        String to = "af304e6259984a857d43406efa1d6528f4055a69";
        BigInteger value = BigInteger.ONE;
        final RawTransaction rawTransaction = RawTransaction.createTransferTransaction(
                nonce, gasPrice, gasLimit, to, value
        );
        String rawTransactionHash = rawTransaction.getRawHash();

        // when
        RawTransaction decoded = RawTransaction.fromRawHash(rawTransactionHash);

        // then
        assertThat(decoded).isNotNull();
        assertThat(decoded.getNonce()).isEqualTo(nonce);
        assertThat(decoded.getGasPrice()).isEqualTo(gasPrice);
        assertThat(decoded.getGasLimit()).isEqualTo(gasLimit);
        assertThat(decoded.getTo()).isEqualTo(to);
        assertThat(decoded.getValue()).isEqualTo(value);
        assertThat(decoded.getBase()).isEqualTo(Type.MAIN.getValue());
        assertThat(decoded.getTarget()).isEqualTo(Type.MAIN.getValue());
        assertThat(decoded.getV()).isNull();
        assertThat(decoded.getR()).isNull();
        assertThat(decoded.getS()).isNull();
    }

    @Test
    public void testDecodeRawTransactionWithSignature() {
        // given
        BigInteger nonce = BigInteger.ZERO;
        BigInteger gasPrice = BigInteger.ONE;
        BigInteger gasLimit = BigInteger.TEN;
        String to = "af304e6259984a857d43406efa1d6528f4055a69";
        BigInteger value = BigInteger.ONE;
        final RawTransaction rawTransaction = RawTransaction.createTransferTransaction(
                nonce, gasPrice, gasLimit, to, value
        );
        ECKeyPair keyPair = ECKeyPair.create(BigInteger.valueOf(3360));
        Signer.signTransaction(rawTransaction, keyPair);

        assertThat(rawTransaction.getV()).isNotNull();
        assertThat(rawTransaction.getR()).isNotNull();
        assertThat(rawTransaction.getS()).isNotNull();

        String rawTransactionHash = rawTransaction.getRawHash();

        // when
        RawTransaction decoded = RawTransaction.fromRawHash(rawTransactionHash);

        // then
        assertThat(decoded).isNotNull();
        assertThat(decoded.getNonce()).isEqualTo(nonce);
        assertThat(decoded.getGasPrice()).isEqualTo(gasPrice);
        assertThat(decoded.getGasLimit()).isEqualTo(gasLimit);
        assertThat(decoded.getTo()).isEqualTo(to);
        assertThat(decoded.getValue()).isEqualTo(value);
        assertThat(decoded.getBase()).isEqualTo(Type.MAIN.getValue());
        assertThat(decoded.getTarget()).isEqualTo(Type.MAIN.getValue());
        assertThat(decoded.getV()).isEqualTo(rawTransaction.getV());
        assertThat(decoded.getR()).isEqualTo(rawTransaction.getR());
        assertThat(decoded.getS()).isEqualTo(rawTransaction.getS());
    }
}
