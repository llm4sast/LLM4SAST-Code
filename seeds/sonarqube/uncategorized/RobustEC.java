import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;

public class RobustEC {
  public static void main(String[] args) {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
      ECGenParameterSpec ellipticCurveName = new ECGenParameterSpec("secp112r1"); // Noncompliant
      keyPairGenerator.initialize(ellipticCurveName);
    } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
    }
  }
}
