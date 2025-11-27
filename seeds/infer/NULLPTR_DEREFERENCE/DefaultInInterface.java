class DefaultInInterface {
  static interface I {
    default Object defaultMethod1() { return null; }
    default Object defaultMethod2() { return "foo"; }
  }
  public static class A implements I {
    public void defaultCallNPE() { System.out.println(this.defaultMethod1().toString()); }
    public void defaultCallOk() { System.out.println(this.defaultMethod2().toString()); }
  }
  public static class B extends A {
    public Object defaultMethod1() { return "foo"; }
    public Object defaultMethod2() { return null; }
    public void overridenCallOk() { System.out.println(this.defaultMethod1().toString()); }
    public void overridenCallNPE() { System.out.println(this.defaultMethod2().toString()); }
  }
  static void uncertainCallMethod1NPE(int i) {
    A aAorB = new A();
    if (i > 0) { aAorB = new B(); }
    System.out.println(aAorB.defaultMethod1().toString());
  }
  static void uncertainCallMethod2NPE(int i) {
    A aAorB = new A();
    if (i > 0) { aAorB = new B(); }
    System.out.println(aAorB.defaultMethod2().toString());
  }
}