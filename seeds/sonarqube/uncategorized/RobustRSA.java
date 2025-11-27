import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RobustRSA {
  public static void main(String[] args) {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(1024);
    } catch (NoSuchAlgorithmException e) {
    }
  }
}
