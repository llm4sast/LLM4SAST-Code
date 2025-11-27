import nonnull.*;
import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
interface A { public void f(@NonNull Object obj, @NonNull Object obj2); }
interface B extends A {
    @Override
    public void f(@NonNull Object obj, @CheckForNull Object obj2);
}
interface C extends A { }
public class TestNonNull implements B {
    public void report(A a) { a.f(null, new Object()); }
    public void doNotReport(B b) { b.f(new Object(), null); }
    public void report2(B b) { b.f(null, new Object()); }
    public void report3(C c) { c.f(new Object(), null); }
    @Override
    public void f(Object obj, Object obj2) {
        System.out.println(obj.hashCode()); 
        System.out.println(obj2.hashCode()); 
    }
}