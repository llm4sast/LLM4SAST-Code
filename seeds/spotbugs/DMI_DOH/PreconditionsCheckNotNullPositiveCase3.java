import com.google.errorprone.bugpatterns.*;
import static com.google.common.base.Preconditions.checkNotNull;
public class PreconditionsCheckNotNullPositiveCase3 {
  public void error() { checkNotNull("string literal"); }
}