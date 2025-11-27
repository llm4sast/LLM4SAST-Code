import codetoanalyze.java.infer.*;
class A { int i; }
class NullPointerExceptionsMoreTests {
  int testNullStringDereferencedBad() {
    String s = new String("abc");
    int j = s.length();
    s = null;
    j = j + s.length();
    return 42;
  }
  int testBrachesAvoidNullPointerExceptionOK(int k) {
    String s = new String("abc");
    int j = 100;
    if (k > 10) { s = null; }
    if (k == 10) { j = s.length(); }
    return j;
  }
  int FN_testParameterRec(A arg, int k) {
    arg.i = 17;
    if (k > 0) { return this.FN_testParameterRec(null, k - 1); } else { return 430; }
  }
  int FN_testParameterRecBool(A arg, boolean b) {
    arg.i = 17;
    if (b) { return this.FN_testParameterRecBool(null, false); } else { return 430; }
  }
  void testParameterOk(A arg) { arg.i = 17; }
  int testArithmeticOk(int k) {
    String s = new String("abc");
    int j = 100;
    if (k > 10) { s = null; }
    if (k <= 10) { j = s.length(); }
    return j;
  }
  int testArithmeticOneOK(int k) {
    String s = new String("abc");
    int j = 100;
    if (k > 10) { s = null; }
    if ((2 * k) == 20) { j = s.length(); }
    return j;
  }
  int f_ident(int k) { return k; }
  int FP_testArithmeticTwo(int k) {
    String s = new String("abc");
    int j = 100;
    if (k > 10) { s = null; }
    if ((f_ident(k)) <= 10) { j = s.length(); }
    return j;
  }
}