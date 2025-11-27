import com.google.errorprone.bugpatterns.*;
public class DeadExceptionPositiveCases {
  public void error() { new RuntimeException("Not thrown, and reference lost"); }
  public void fixIsToDeleteTheFirstStatement() {
    new IllegalArgumentException("why is this here?");
    int i = 1;
    System.out.println("i = " + i);
  }
  public void firstStatementWithNoSurroundingBlock() {
    if (true)
      new InterruptedException("this should be thrown");
    if (true)
      return;
    else
      new ArithmeticException("should also be thrown");
  }
}