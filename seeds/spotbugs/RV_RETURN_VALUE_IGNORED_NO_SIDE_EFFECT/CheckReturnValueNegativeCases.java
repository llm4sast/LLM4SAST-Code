import com.google.errorprone.bugpatterns.*;
public class CheckReturnValueNegativeCases {
  public void test1() {
    test2();
    Object obj = new String();
    obj.toString();
  }
  @SuppressWarnings("foo")  
  public void test2() { }
}