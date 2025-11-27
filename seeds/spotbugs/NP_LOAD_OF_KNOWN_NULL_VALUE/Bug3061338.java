import sfBugs.*;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NonNull;
public class Bug3061338 {
    @ExpectWarning("NP_NONNULL_PARAM_VIOLATION")
    public void doesntWork() {
        Object two = null;
        for (int i = 0; i < 5; i++) { getTwo(two); }
    }
    @ExpectWarning("NP_NONNULL_PARAM_VIOLATION")
    public void alreadyWorks() {
        Object two = null;
        for (int i = 0; i < 5; i++) {
            two = null;
            getTwo(two);
        }
    }
    public void getTwo(@NonNull Object x) { }
}