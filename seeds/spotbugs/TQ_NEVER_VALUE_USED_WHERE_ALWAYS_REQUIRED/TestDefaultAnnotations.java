import jsr305.*;
import jsr305.Foo;
import javax.annotation.meta.When;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
@DefaultFooParameters
public class TestDefaultAnnotations {
    public void requiresFoo(Object o) { }
    @ExpectWarning("TQ")
    public void violate(@Foo(when = When.NEVER) Object x) { requiresFoo(x); }
}