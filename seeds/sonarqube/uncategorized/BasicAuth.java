import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

class BasicAuth {

  public void foo(String apiUrl) throws Exception {
    String encoded = Base64.getEncoder().encodeToString("login:passwd".getBytes());
    URL url = new URL(apiUrl);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setDoOutput(true);
    conn.setRequestProperty("Authorization", "Basic " + encoded); // Noncompliant
  }
}
