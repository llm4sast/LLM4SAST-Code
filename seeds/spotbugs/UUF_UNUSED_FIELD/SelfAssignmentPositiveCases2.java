import com.google.errorprone.bugpatterns.*;
public class SelfAssignmentPositiveCases2 {
  private int a;
  private Foo foo;
  public void test6() {
    Foo foo = new Foo();
    foo.a = 2;
    foo.a = foo.a;
  }
  public void test7() {
    Foobar f = new Foobar();
    f.foo = new Foo();
    f.foo.a = 10;
    f.foo.a = f.foo.a;
  }
  public void test8() {
    foo = new Foo();
    this.foo.a = foo.a;
  }
  public void test9(Foo fao, Foo bar) { this.foo = foo; }
  public void test10(Foo foo) { foo = foo; }
  private static class Foo { int a; }
  private static class Bar { int a; }
  private static class Foobar {
    Foo foo;
    Bar bar;
  }
}