class ThreadSensitivity {
  Object monitorA, monitorB;
  void conditionalAssertMainThread_Bad(boolean b) {
    if (b) {
      OurThreadUtils.assertMainThread();
      synchronized (monitorA) { synchronized (monitorB) { } }
    } else {
      synchronized (monitorB) { synchronized (monitorA) { } }
    }
  }
  Object monitorC, monitorD;
  void conditionalIsMainThread_Ok() {
    if (OurThreadUtils.isMainThread()) {
      synchronized (monitorC) { synchronized (monitorD) { } }
    }
  }
  void conditionalIsUiThread_Ok() {
    if (OurThreadUtils.isUiThread()) {
      synchronized (monitorD) { synchronized (monitorC) { } }
    }
  }
  Object monitorE, monitorF;
  void conditionalNegatedIsMainThread_Bad() {
    if (!OurThreadUtils.isMainThread()) {
      synchronized (monitorE) { synchronized (monitorF) { } }
    } else {
      synchronized (monitorF) { synchronized (monitorE) { } }
    }
  }
  Object monitorG, monitorH;
  public void confusedAssertBad(boolean b, boolean c) {
    if (b) { OurThreadUtils.assertOnBackgroundThread(); } else { OurThreadUtils.assertOnUiThread(); }
    if (c) {
      synchronized (monitorG) { synchronized (monitorH) { } }
    } else {
      synchronized (monitorH) { synchronized (monitorG) { } }
    }
  }
  Object monitorI, monitorJ;
  public void FP_confusedAssertOk(boolean b) {
    if (b) { OurThreadUtils.assertOnBackgroundThread(); }
    if (b) {
      synchronized (monitorI) { synchronized (monitorJ) { } }
    }
    if (b) {
      synchronized (monitorJ) { synchronized (monitorI) { } }
    }
  }
}