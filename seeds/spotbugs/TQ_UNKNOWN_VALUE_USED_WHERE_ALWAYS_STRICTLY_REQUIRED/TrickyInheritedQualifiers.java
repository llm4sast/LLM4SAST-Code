import jsr305.*;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class TrickyInheritedQualifiers {
    static class X {
        public void f(@Strict Object p) { }
    }
    static class Y extends X {
        @Override
        public void f(Object p) { }
    }
    @ExpectWarning("TQ")
    public void violateEasy(X x, Object q) { x.f(q); }
    @ExpectWarning("TQ")
    public void violateTricky(Y y, Object q) { y.f(q); }
    @ExpectWarning("TQ")
    public void violateWorksAccidentally(Y y, Object q, @Strict Object r) { y.f(q); }
}