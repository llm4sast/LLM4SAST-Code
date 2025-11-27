import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class VerifyHostName {
  public void sendMail(String message) {
    Email email = new SimpleEmail();
    email.setMsg(message);
    email.setSmtpPort(465);
    email.setAuthenticator(new DefaultAuthenticator(username, password));
    email.setSSLOnConnect(true); // Noncompliant
    email.send();
  }
}
