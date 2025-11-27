import sfBugs.*;
import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
public class Bug3258757 {
    public static void main(String[] args) {
        final String str = getString();
        if(System.currentTimeMillis() > 0) { useString(str); }
        useString(str);
    }
    public static void other1(@CheckForNull String str) {
        if(System.currentTimeMillis() > 0) { useString(str); }
    }
    public static void other2(@CheckForNull String str) { useString(str); }
    @CheckForNull
    public static String getString() { return null; }
    private static void useString(@NonNull String str) { }
}