import com.google.errorprone.bugpatterns.*;
public class DeadExceptionNegativeCases {
  public void noError() {
    Exception e = new RuntimeException("stored");
    e = new UnsupportedOperationException("also stored");
    throw new IllegalArgumentException("thrown");
  }
  public Exception returnsException() { return new RuntimeException("returned"); }
}