import jsr305.*;
import jsr305.Foo;
import jsr305.Bar;
import jsr305.I1;
import jsr305.I2;
import javax.annotation.Tainted;
import javax.annotation.meta.When;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import jsr305.package1.InterfaceWithDefaultUntaintedParams;
public class TestViolatedInheritedAnnotations implements I1, I2 {
    @Foo(when = When.ALWAYS)
    Object always;
    @Foo(when = When.NEVER)
    Object never;
    @Bar(when = When.MAYBE, strArrField = { "yip", "yip" }, cField = 'Q', eArrField = { When.UNKNOWN })
    Object barField;
    @Override
    @ExpectWarning("TQ")
    public Object alwaysReturnFoo1() { return never; }
    @Override
    @ExpectWarning("TQ")
    public Object neverReturnFoo1() { return always; }
    @Override
    @ExpectWarning("TQ")
    public Object alwaysReturnFooParams1(Object alwaysParam, Object neverParam) { return neverParam; }
    @ExpectWarning("TQ")
    public void needsUntaintedParam(@Tainted Object tainted, InterfaceWithDefaultUntaintedParams obj) { obj.requiresUntaintedParam(tainted); }
    static class X {
        public @Tainted
        Object f() { return new Object(); }
    }
    static class Y extends X {
        @Override
        public Object f() { return new Object(); }
    }
    @ExpectWarning("TQ")
    public void easyViolation(InterfaceWithDefaultUntaintedParams obj) {
        X x = new X();
        obj.requiresUntaintedParam(x.f()); 
    }
    @ExpectWarning("TQ")
    public void trickyViolation(InterfaceWithDefaultUntaintedParams obj) {
        Y y = new Y();
        obj.requiresUntaintedParam(y.f()); 
    }
}