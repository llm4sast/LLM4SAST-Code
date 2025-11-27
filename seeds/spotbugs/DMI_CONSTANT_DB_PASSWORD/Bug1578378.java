import sfBugs.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Bug1578378 {
    public abstract static class MyCallableStatement implements CallableStatement { }
    public void doit() {
        Connection conn = null;
        CallableStatement stmt = null;
        MyCallableStatement stmt2 = null;
        try {
            conn = DriverManager.getConnection("url", "user", "password");
            stmt = conn.prepareCall("xxx");
            stmt2 = (MyCallableStatement) conn.prepareCall("xxx");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
            if (stmt2 != null) {
                try {
                    stmt2.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}