import com.google.errorprone.bugpatterns.*;
public class SelfAssignmentPositiveCases1 {
  private int a;
  public void test1(int b) { this.a = a; }
  public void test2(int b) { a = this.a; }
  public void test3() {
    int a = 0;
    a = a;
  }
  public void test4() { this.a = this.a; }
  public void test5() {
    if ((a = a) != 10) { System.out.println("foo"); }
  }
}