import codetoanalyze.java.infer.*;
class D extends Exception {
  D(int data) { this.data = data; }
  int data;
}
class InterExceptions {
  private static int g(int x) throws D {
    if (x < 0) { throw new D(x); }
    return x + 22;
  }
  private static int caller_of_g(int x) {
    try { return g(x - 10); } catch (D d) { return d.data; }
  }
  private static void testInterExceptions1Bad() {
    Integer my_null_int = null;
    if (caller_of_g(100) == 112) { my_null_int.intValue(); }
  }
  private static void testInterExceptions1Good() {
    Integer my_null_int = null;
    if (caller_of_g(100) != 112) { my_null_int.intValue(); }
  }
  private static void testInterExceptions2Bad() {
    Integer my_null_int = null;
    if (caller_of_g(0) == -10) { my_null_int.intValue(); }
  }
  private static void testInterExceptions2Good() {
    Integer my_null_int = null;
    if (caller_of_g(0) != -10) { my_null_int.intValue(); }
  }
  private static int thrower(int x) throws D { throw new D(x); }
  private static int caller_of_thrower(int x) {
    try { return thrower(x + 23); } catch (D d) { return d.data; }
  }
  private static void testInterExceptions3Bad() {
    Integer my_null_int = null;
    if (caller_of_thrower(10) == 33) { my_null_int.intValue(); }
  }
  private static void testInterExceptions3Good() {
    Integer my_null_int = null;
    if (caller_of_thrower(10) != 33) { my_null_int.intValue(); }
  }
}