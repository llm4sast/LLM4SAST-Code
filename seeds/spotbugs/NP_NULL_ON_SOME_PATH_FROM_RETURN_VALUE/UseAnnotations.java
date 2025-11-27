import nullnessAnnotations.*;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
public class UseAnnotations {
    @CheckForNull
    Object f(int x) {
        if (x == 0)
            return null;
        return x;
    }
    int g(@Nonnull Object x) { return 42; }
    int foo() { return g(f(12)); }
    int foo2() { return g(null); }
    int foo3(int x) {
        Object y = null;
        if (x > 0)
            y = "";
        return g(y); 
    }
    int foo4() { return f(12).hashCode(); }
    int bar() { return f(12).hashCode(); }
    int bar2() {
        Object x = null;
        return x.hashCode(); 
    }
    int bar3(int x) {
        Object y = null;
        if (x > 0)
            y = "";
        return y.hashCode(); 
    }
    int bar4(int x) {
        Object y = null;
        if (x > 0)
            y = "";
        return y.hashCode(); 
    }
}