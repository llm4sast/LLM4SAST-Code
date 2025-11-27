import codetoanalyze.java.infer.*;
class E extends Exception {
  E(int data) { this.data = data; }
  int data;
}
class IntraExceptions {
  private static int intra_exceptions_1(int x) {
    try {
      int y = x + 10;
      if (y < 0) throw new E(y);
      return 10;
    } catch (E e) { return e.data; }
  }
  private static void testIntraExceptions1Bad() {
    Integer my_null_int = null;
    if (intra_exceptions_1(-100) == -90) { my_null_int.intValue(); }
  }
  private static void testIntraExceptions1Good() {
    Integer my_null_int = null;
    if (intra_exceptions_1(-100) == 0) { my_null_int.intValue(); }
  }
  private static int intra_exceptions_2(int x) {
    try { throw new E(x + 22); } catch (E e) { return e.data; }
  }
  private static void testIntraExceptions2Bad() {
    Integer my_null_int = null;
    if (intra_exceptions_2(10) == 32) { my_null_int.intValue(); }
  }
  private static void testIntraExceptions2Good() {
    Integer my_null_int = null;
    if (intra_exceptions_2(10) == 0) { my_null_int.intValue(); }
  }
}