import sfBugs.*;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class Bug3383709 {
    @ExpectWarning("NP_LOAD_OF_KNOWN_NULL_VALUE")
    public int foo1(Object obj1, Object obj2) {
        if (obj2 != null) { return 1; }
        if (obj1 != null && obj2 == null) { return 2; }
        return 0;
    }
    @ExpectWarning("NP_LOAD_OF_KNOWN_NULL_VALUE")
    public int foo2(Object obj1, Object obj2) {
        if (obj2 != null) { return 1; }
        if (obj1 != null
                && obj2 == null) { return 2; }
        return 0;
    }
}