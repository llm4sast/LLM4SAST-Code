import sfBugs.*;
import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import edu.umd.cs.findbugs.annotations.DesireNoWarning;
public class Bug2612987 {
    @Nonnegative
    private int nonNegativeValue = 1;
    @Nonnegative
    public int get() { return nonNegativeValue; }
    @DesireNoWarning("TQ")
    public void set(@CheckForSigned int possibleNegativeValue) {
        if (possibleNegativeValue >= 0)
            nonNegativeValue = possibleNegativeValue;
    }
}