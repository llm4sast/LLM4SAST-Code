import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class SecureEnctyption {
  public static void main(String[] args) {
    try {
      Cipher.getInstance("AES/CBC/PKCS5Padding"); // Noncompliant
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      // ...
    }
    try {
      Cipher.getInstance("RSA/None/NoPadding"); // Noncompliant
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      // ...
    }
  }
}
