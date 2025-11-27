import com.google.errorprone.bugpatterns.*;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Preconditions;
public class PreconditionsCheckNotNullPositiveCase1 {
  public void error() {
    Preconditions.checkNotNull("string literal");
    String thing = null;
    checkNotNull("thing is null", thing);
    Preconditions.checkNotNull("a string literal " + "that's got two parts", thing);
  }
}