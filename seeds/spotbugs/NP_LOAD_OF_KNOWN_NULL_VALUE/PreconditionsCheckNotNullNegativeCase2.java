import com.google.errorprone.bugpatterns.*;
import com.google.common.base.Preconditions;
public class PreconditionsCheckNotNullNegativeCase2 {
  public void go() {
    Object testObj = null;
    Preconditions.checkNotNull(testObj, "this is ok");
  }
}