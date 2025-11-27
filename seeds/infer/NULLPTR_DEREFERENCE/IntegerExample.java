import codetoanalyze.java.infer.*;
class IntegerExample {
  private static void testIntegerEqualsMethodGood() {
    Integer a = new Integer(42);
    Integer b = new Integer(42);
    Integer c = null;
    if (!a.equals(b)) { c.intValue(); }
  }
  private static void testIntegerEqualsMethodBad() {
    Integer a = new Integer(42);
    Integer b = new Integer(42);
    Integer c = null;
    if (a.equals(b)) { c.intValue(); }
  }
  private static void FP_testIntegerBuiltInEqualOperatorCachedValuesOk() {
    Integer a = new Integer(42);
    Integer b = 127;
    Integer c = 127;
    Integer d = null;
    if (a != 42) { d.intValue(); }
    if (b != 127) { d.intValue(); }
    if (b != c) { d.intValue(); }
  }
  private static void testIntegerBuiltInEqualOperatorNonCachedValuesBad() {
    Integer a = 128;
    Integer b = 128;
    Integer c = null;
    if (a != b) { c.intValue(); }
  }
  private static void testIntegerEqualsMethodMaxValueBad() {
    Integer a = new Integer(2147483647);
    Integer b = new Integer(2147483647);
    Integer c = null;
    if (a.equals(b)) { c.intValue(); }
  }
  private static void testIntegerBuiltInEqualOperatorMaxValueOk() {
    Integer a = new Integer(2147483647);
    Integer b = null;
    if (a != 2147483647) { b.intValue(); }
  }
}