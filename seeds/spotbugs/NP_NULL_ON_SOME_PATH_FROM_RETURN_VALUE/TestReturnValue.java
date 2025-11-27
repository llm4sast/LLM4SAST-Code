import nonnull.*;
import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
abstract class Foo {
    protected abstract @CheckForNull
    Object getCheckForNull();
    public abstract @NonNull
    Object reportReturnNull();
}
abstract class Bar extends Foo { }
public abstract class TestReturnValue {
    public void report(Foo f) {
        Object obj = f.getCheckForNull();
        System.out.println(obj.hashCode());
    }
    public void reportCallThroughSubclass(Bar b) {
        Object obj = b.getCheckForNull();
        System.out.println(obj.hashCode());
    }
    public Object reportNonNull() { return null; }
    public @NonNull
    Object reportNonNull2() { return null; }
    public @CheckForNull
    Object doNotReportReturnCheckForNull() { return null; }
}