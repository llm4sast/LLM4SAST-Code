import sfBugs.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class Bug2475589 {
    private Connection con;
    static final String staticFinalQuery = "select * from table where";
    public Bug2475589(Connection newCon) { con = newCon; }
    @ExpectWarning("SQL")
    public void runUserQuery1(String userQuery) {
        String newQuery = staticFinalQuery + userQuery;
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(newQuery);
        } catch (Exception e) { System.out.println("Got exception"); }
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (Exception e) { System.out.println("Got exception"); }
    }
    @ExpectWarning("SQL")
    public void runUserQuery2(String userQuery) {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(staticFinalQuery + userQuery);
        } catch (Exception e) { System.out.println("Got exception"); }
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (Exception e) { System.out.println("Got exception"); }
    }
    @ExpectWarning("SQL")
    public void runUserQuery3(String userQuery) {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("select * from table where " + userQuery);
        } catch (Exception e) { System.out.println("Got exception"); }
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (Exception e) { System.out.println("Got exception"); }
    }
    public void runUserQuery4(String userQuery) {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(userQuery + " where val = 1;");
        } catch (Exception e) { System.out.println("Got exception"); }
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (Exception e) { System.out.println("Got exception"); }
    }
    public void runUserQuery5(String userQuery) {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(userQuery + "'");
        } catch (Exception e) { System.out.println("Got exception"); }
        try {
            if (pstmt != null)
                pstmt.close();
        } catch (Exception e) { System.out.println("Got exception"); }
    }
}