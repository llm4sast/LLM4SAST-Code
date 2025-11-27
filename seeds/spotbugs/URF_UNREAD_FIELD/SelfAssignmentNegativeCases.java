import com.google.errorprone.bugpatterns.*;
public class SelfAssignmentNegativeCases {
  private int a;
  public void test1(int a) { this.a = a; }
  public void test2() {
    int a = 0;
    int b = a;
    a = b;
  }
  public void test3() { int a = 10; }
  public void test4() {
    int i = 1;
    i += i;
  }
  public void test5(SelfAssignmentNegativeCases n) { a = n.a; }
  public void test6() {
    Foo foo = new Foo();
    Bar bar = new Bar();
    foo.a = bar.a;
  }
  public void test7() {
    Foobar f1 = new Foobar();
    f1.foo = new Foo();
    f1.bar = new Bar();
    f1.foo.a = f1.bar.a;
  }
  public void test8(SelfAssignmentNegativeCases that) { this.a = that.a; }
  private static class Foo { int a; }
  private static class Bar { int a; }
  private static class Foobar {
    Foo foo;
    Bar bar;
  }
}