import bugPatterns.*;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class ICAST_INT_CAST_TO_DOUBLE_PASSED_TO_CEIL {
    @ExpectWarning("ICAST_INT_CAST_TO_DOUBLE_PASSED_TO_CEIL")
    public double bug(int x) { return Math.ceil(x); }
    @ExpectWarning("ICAST_INT_CAST_TO_DOUBLE_PASSED_TO_CEIL")
    public double bug(long x) { return Math.ceil(x); }
}