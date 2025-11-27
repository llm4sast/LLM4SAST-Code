import java.security.SecureRandom;

public class SecureRandomCase {
  public void foo1() {
    SecureRandom sr = new SecureRandom();
    sr.setSeed(123456L); // Noncompliant
    int v = sr.nextInt(32);
  }

  public void foo2() throws Exception {
    SecureRandom sr = new SecureRandom("abcdefghijklmnop".getBytes("us-ascii")); // Noncompliant
    int v = sr.nextInt(32);
  }
}
