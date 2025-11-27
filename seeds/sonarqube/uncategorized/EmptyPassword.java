import java.sql.Connection;
import java.sql.DriverManager;

public class EmptyPassword {
  public void foo() throws Exception {
    Connection conn =
        DriverManager.getConnection("jdbc:derby:memory:myDB;create=true", "login", "");
    System.out.println(conn);
  }
}
