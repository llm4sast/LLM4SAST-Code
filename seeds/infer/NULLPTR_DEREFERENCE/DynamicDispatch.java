import codetoanalyze.java.infer.*;
class DynamicDispatch {
  static interface Interface { public Object foo(); }
  static class Impl implements Interface {
    @Override
    public Object foo() { return null; }
  }
  static final class FinalImpl extends Impl {}
  static void interfaceShouldNotCauseFalseNegativeEasyBad() {
    Interface i = new Impl();
    i.foo().toString();
  }
  static void FN_interfaceShouldNotCauseFalseNegativeHardOK(Interface i) { i.foo().toString(); }
  static void callWithBadImplementationBad_FN(Impl impl) { FN_interfaceShouldNotCauseFalseNegativeHardOK(impl); }
  static void callWithBadFinalImplementationBad(FinalImpl impl) { FN_interfaceShouldNotCauseFalseNegativeHardOK(impl); }
  static class Supertype {
    Object foo() { return new Object(); }
    Object bar() { return null; }
  }
  static class Subtype extends Supertype {
    @Override
    Object foo() { return null; }
    @Override
    Object bar() { return new Object(); }
  }
  static void dynamicDispatchShouldNotCauseFalseNegativeEasyBad() {
    Supertype o = new Subtype();
    o.foo().toString();
  }
  static void dynamicDispatchShouldNotCauseFalsePositiveEasyOK() {
    Supertype o = new Subtype();
    o.bar().toString();
  }
  static void dynamicDispatchShouldNotReportWhenCallingSupertypeOK(Supertype o) { o.foo().toString(); }
  static void dynamicDispatchShouldReportWhenCalledWithSubtypeParameterBad_FN(Subtype o) { dynamicDispatchShouldNotReportWhenCallingSupertypeOK(o); }
  static Object dynamicDispatchWrapperFoo(Supertype o) { return o.foo(); }
  static Object dynamicDispatchWrapperBar(Supertype o) { return o.bar(); }
  static void dynamicDispatchCallsWrapperWithSupertypeOK() {
    Supertype o = new Supertype();
    dynamicDispatchWrapperFoo(o).toString();
  }
  static void dynamicDispatchCallsWrapperWithSupertypeBad() {
    Supertype o = new Supertype();
    dynamicDispatchWrapperBar(o).toString();
  }
  static void dynamicDispatchCallsWrapperWithSubtypeBad() {
    Supertype o = new Subtype();
    dynamicDispatchWrapperFoo(o).toString();
  }
  static void dynamicDispatchCallsWrapperWithSubtypeOK() {
    Supertype o = new Subtype();
    dynamicDispatchWrapperBar(o).toString();
  }
  static class WithField {
    Supertype mField;
    WithField(Supertype t) { mField = t; }
    static void dispatchOnFieldOK() {
      Supertype subtype = new Subtype();
      WithField object = new WithField(subtype);
      object.mField.bar().toString();
    }
    static void dispatchOnFieldBad() {
      Supertype subtype = new Subtype();
      WithField object = new WithField(subtype);
      object.mField.foo().toString();
    }
  }
  private Object callFoo(Supertype o) { return o.foo(); }
  void dynamicResolutionWithPrivateMethodBad() {
    Supertype subtype = new Subtype();
    callFoo(subtype).toString();
  }
  Object variadicMethod(Supertype... args) {
    if (args.length == 0) { return null; } else { return args[0].foo(); }
  }
  void dynamicResolutionWithVariadicMethodBad() {
    Supertype subtype = new Subtype();
    variadicMethod(subtype, null, null).toString();
  }
}
class InheritanceDispatch {
  class A { int foo() { return 32; } }
  class B extends A { int foo() { return 52; } }
  class C extends B {}
  A getB() { return new B(); }
  A getC() { return new C(); }
  void dispatch_to_B_ok() {
    A b = getB();
    if (b.foo() == 32) {
      Object o = null;
      o.toString();
    }
  }
  void dispatch_to_B_bad() {
    A b = getB();
    if (b.foo() == 52) {
      Object o = null;
      o.toString();
    }
  }
  void dispatch_to_A_bad() {
    A a = new A();
    if (a.foo() == 32) {
      Object o = null;
      o.toString();
    }
  }
  void dispatch_to_C_bad() {
    A c = getC();
    if (c.foo() == 52) {
      Object o = null;
      o.toString();
    }
  }
}
class Specialization {
  static class C { C f; }
  abstract static class A {
    abstract C buildC();
    C callBuildC(A a) { return a.buildC(); }
  }
  static class A_Good extends A { C buildC() { return new C(); } }
  static class A_Bad extends A { C buildC() { return null; } }
  C callBuildCGood(A a) { return a.buildC(); }
  C buildCAndDerefBad() { return callBuildCGood(new A_Bad()).f; }
  C buildCAndDerefGood() { return callBuildCGood(new A_Good()).f; }
  static class Box1 {
    Box2 f1;
    Box1(Box2 f1) { this.f1 = f1; }
  }
  static class Box2 {
    Box3 f2;
    Box2(Box3 f2) { this.f2 = f2; }
  }
  static class Box3 {
    A f3;
    Box3(A f3) { this.f3 = f3; }
  }
  C callBuildCOnBoxGood(Box1 box) { return box.f1.f2.f3.buildC(); }
  C buildCOnBoxAndDerefBad() { return callBuildCOnBoxGood(new Box1(new Box2(new Box3(new A_Bad())))).f; }
  C buildCOnBoxAndDerefGood() { return callBuildCOnBoxGood(new Box1(new Box2(new Box3(new A_Good())))).f; }
  C callCallBuildC(A a1, A a2) { return a1.callBuildC(a2); }
  C buildCTransitivelyAndDerefBad() { return callCallBuildC(new A_Good(), new A_Bad()).f; }
  C buildCTransitivelyAndDerefGood() { return callCallBuildC(new A_Bad(), new A_Good()).f; }
  C callBuildCWithEmptyPaths(A a, boolean b) { return b ? a.buildC(): null; }
  C buildCWithInfeasiblePathsAndDerefBad() { return callBuildCWithEmptyPaths(new A_Bad(), true).f; }
  C buildCWithInfeasiblePathsAndDerefGood() { return callBuildCWithEmptyPaths(new A_Good(), true).f; }
  C callBuildCTwiceGood(A a1, A a2) {
    C c = (a1 == null) ? null : a1.buildC();
    return (a2 == null) ? null : a2.buildC();
  }
  C buildCAndDerefNeedPartialSpecializationBad(A a) { return callBuildCTwiceGood(a, new A_Bad()).f; }
  C buildCAndDerefNeedPartialSpecializationGood(A a) { return callBuildCTwiceGood(a, new A_Good()).f; }
}