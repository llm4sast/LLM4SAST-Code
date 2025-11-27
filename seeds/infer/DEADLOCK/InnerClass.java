class InnerClass {
  synchronized void outerInnerOk(InnerClassA a) { a.lockOuter(); }
  synchronized void lockOuter() {}
  synchronized void FP_outerInnerOk(InnerClassA a) { a.lockInner(); } // DEADLOCK 1
  class InnerClassA {
    void lockOuter() { synchronized (InnerClass.this) { } }
    void outerInnerOk() { synchronized (InnerClass.this) { InnerClass.this.lockOuter(); } }
    synchronized void lockInner() {}
    synchronized void innerOuterBad() { InnerClass.this.lockOuter(); }
    InnerClassA() { synchronized (InnerClass.this) { InnerClass.this.lockOuter(); } }
    InnerClassA(Object o) { synchronized (this) { InnerClass.this.lockOuter(); } }
  }
}