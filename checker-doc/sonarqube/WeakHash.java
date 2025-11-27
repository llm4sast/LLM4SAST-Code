import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WeakHash {
    public void foo() {
        try {
            MessageDigest md1 = MessageDigest.getInstance("SHA");
            MessageDigest md2 = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}