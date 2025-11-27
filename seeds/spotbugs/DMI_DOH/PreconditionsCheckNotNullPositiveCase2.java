import com.google.errorprone.bugpatterns.*;
public class PreconditionsCheckNotNullPositiveCase2 {
  public void error() { com.google.common.base.Preconditions.checkNotNull("string literal"); }
}