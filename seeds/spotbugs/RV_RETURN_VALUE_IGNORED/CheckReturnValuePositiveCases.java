import com.google.errorprone.bugpatterns.*;
import javax.annotation.CheckReturnValue;
public class CheckReturnValuePositiveCases {
  IntValue intValue = new IntValue(0);
  @CheckReturnValue
  private int increment(int bar) { return bar + 1; }
  public void foo() {
    int i = 1;
    increment(i);
    System.out.println(i);
  }
  public void bar() { this.intValue.increment(); }
  public void testIntValue() {
    IntValue value = new IntValue(10);
    value.increment();
  }
  private class IntValue {
    final int i;
    public IntValue(int i) { this.i = i; }
    @CheckReturnValue
    public IntValue increment() { return new IntValue(i + 1); }
    public void increment2() { this.increment(); }
    public void increment3() { increment(); }
  }
}