import sfBugs.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import edu.umd.cs.findbugs.annotations.DesireNoWarning;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug2382279 {
    private Connection con;
    public Bug2382279(Connection newCon) { con = newCon; }
    @DesireNoWarning("ODR")
    @NoWarning("OBL_UNSATISFIED_OBLIGATION")
    @ExpectWarning("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
    public ResultSet execute(String query) throws SQLException {
        ResultSet rs;
        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException sqle) { System.out.println("Couldn't execute statement"); }
        return null;
    }
}