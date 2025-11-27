import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
public class SQL_NONCONSTANT_STRING_PASSED_TO_EXECUTE_patch0 {
    ResultSet g(Connection conn) throws Exception {
        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery("FOOBAR");
    }
    public boolean testSqlLongOk(Connection c, String code) throws SQLException {
        return Sql.hasResult(c, 1L, "SELECT COUNT(*) FROM myTable", 2L, "Code: "+code);
    }
    public static class Sql {
        public static boolean hasResult(Connection c, String query) throws SQLException {
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(query)) {
                return rs.next();
            }
        }
        public static boolean hasResult(Connection c, long l1, String query, long l2, String somethingElse) throws SQLException {
            System.out.println(somethingElse+": "+l1+":"+l2);
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery(query)) {
                return rs.next();
            }
        }
    }
}
