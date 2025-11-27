import com.google.errorprone.bugpatterns.*;
import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.base.Preconditions;
public class PreconditionsTooManyArgsPositiveCase1 {
  int foo;
  public void checkPositive(int x) { checkArgument(x > 0, "%d > 0", x); }
  public void checkFoo() { Preconditions.checkState(foo == 0, "foo must be equal to 0 but was {0}", foo); }
}