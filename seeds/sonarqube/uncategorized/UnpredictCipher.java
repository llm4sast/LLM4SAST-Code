import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class UnpredictCipher {
  public void encrypt(String key, String plainText) {
    byte[] RandomBytes = "7cVgr5cbdCZVw5WY".getBytes(StandardCharsets.UTF_8);
    IvParameterSpec iv = new IvParameterSpec(RandomBytes);
    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    try {
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
    } catch (NoSuchAlgorithmException
        | InvalidKeyException
        | NoSuchPaddingException
        | InvalidAlgorithmParameterException e) {
    }
  }
}
