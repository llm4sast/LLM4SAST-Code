class Parameters {
  private static void syncOnParam(Object x) { synchronized (x) { } }
  public synchronized void oneWaySyncOnParamBad(Object x) { syncOnParam(x); }
  public void otherWaySyncOnParamBad(Object x) {
    synchronized (x) { synchronized (this) { } }
  }
  private static void emulateSynchronized(Parameters self) { synchronized (self) { } }
  Parameters someObject;
  public synchronized void oneWayEmulateSyncBad() { emulateSynchronized(someObject); }
  public void anotherWayEmulateSyncBad() {
    synchronized (someObject) { synchronized (this) { } }
  }
}