import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class FastHash {
  private SecretKey deriveKey(String password, byte[] salt) throws Exception {
    PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 120000, 256); // Noncompliant
    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2withHmacSHA512");
    return secretKeyFactory.generateSecret(keySpec);
  }
}
