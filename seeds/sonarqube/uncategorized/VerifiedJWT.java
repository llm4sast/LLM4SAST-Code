import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class VerifiedJWT {
  private static final String USER_LOGIN = "user123";
  private static final String SECRET_KEY = "mySecretKey";

  public String encode() {
    return Jwts.builder().setSubject(USER_LOGIN).compact();
  }

  public Claims decode(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parse(token).getBody();
  }
}
