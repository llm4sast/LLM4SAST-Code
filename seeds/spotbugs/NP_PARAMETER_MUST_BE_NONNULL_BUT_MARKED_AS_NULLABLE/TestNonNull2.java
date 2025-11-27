import nullnessAnnotations.packageDefault.*;
import javax.annotation.CheckForNull;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
class TestNonNull2 extends TestNonNull1 implements Interface1 {
    @ExpectWarning("NP_NONNULL_PARAM_VIOLATION")
    void report1() { f(null); }
    @NoWarning("NP")
    void report2() { g(null); }
    @NoWarning("NP")
    void ok1() { h(null); }
    @ExpectWarning(value="NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE", num=1)
    public Object k(@CheckForNull Object o) {
        s = o;
        return o;
    }
}