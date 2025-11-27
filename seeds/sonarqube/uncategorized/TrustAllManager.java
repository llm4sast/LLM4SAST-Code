import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

class TrustAllManager implements X509TrustManager {

  @Override
  public void checkClientTrusted(X509Certificate[] chain, String authType)
      throws CertificateException { // Noncompliant
  }

  @Override
  public void checkServerTrusted(X509Certificate[] chain, String authType)
      throws CertificateException { // Noncompliant
  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    return null;
  }
}
