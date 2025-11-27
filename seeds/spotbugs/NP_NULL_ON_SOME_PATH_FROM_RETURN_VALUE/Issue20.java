import lambdas.*;
import javax.annotation.CheckForNull;
public class Issue20 {
  @CheckForNull
  public static String methodThatMightReturnNull() {
    if (Boolean.getBoolean("test")) { return null; }
    return "test";
  }
  public void testNPLambda() {
    Runnable lambda = () -> {
      String maybeNull = methodThatMightReturnNull();
      boolean empty = maybeNull.isEmpty();
      System.out.println(empty);
    };
    lambda.run();
  }
}