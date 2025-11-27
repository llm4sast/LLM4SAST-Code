import org.h2.security.SHA256;

public class HardcodedCredit {
  public void foo() {
    String inputString = "s3cr37";
    byte[] key = inputString.getBytes();
    byte[] message = "test message".getBytes();
    SHA256.getHMAC(key, message);
  }
}
