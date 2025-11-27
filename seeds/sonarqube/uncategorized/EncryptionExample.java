import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionExample {
  public void encrypt(byte[] key, byte[] ptxt) throws Exception {
    byte[] nonce = "7cVgr5cbdCZV".getBytes("UTF-8");
    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
    GCMParameterSpec gcmSpec = new GCMParameterSpec(128, nonce);
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec); // Noncompliant
  }
}
