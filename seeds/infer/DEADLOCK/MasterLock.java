class MasterLock {
  Object a, b;
  void oneWayBad() {
    synchronized (a) { synchronized (b) { } }
  }
  void theOtherWayBad() {
    synchronized (b) { synchronized (a) { } }
  }
  Object master, x, y;
  void oneWayOk() {
    synchronized (master) {
      synchronized (x) { synchronized (y) { } }
    }
  }
  void theOtherWayOk() {
    synchronized (master) {
      synchronized (y) { synchronized (x) { } }
    }
  }
}