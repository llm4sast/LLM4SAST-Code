import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;

public class WeakProtocol {
  public static void main(String[] args) {
    try {
      SSLContext.getInstance("TLSv1.1");
    } catch (NoSuchAlgorithmException e) {
    }
  }
}
