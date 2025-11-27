import sfBugs.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug2207693 {
    private Connection con;
    private String statement;
    public Bug2207693(Connection newCon, String newStatement) {
        con = newCon;
        statement = newStatement;
    }
    @NoWarning("OBL")
    public void execute() {
        try {
            final PreparedStatement ps = con.prepareStatement(statement);
            try {
                final ResultSet rs = ps.executeQuery();
                if (rs != null) { System.out.println("Okay!"); }
            } finally { ps.close(); }
        } catch (Exception e) { System.out.println("Got exception"); }
    }
}