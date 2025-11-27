import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
class ThreadDeadlock {
  Object lockA;
  @UiThread
  public synchronized void noParallelismAOk() { synchronized (lockA) { } }
  @UiThread
  public void noParallelismBOk() {
    synchronized (lockA) { synchronized (this) { } }
  }
  Object lockB;
  @UiThread
  public synchronized void annotatedUiThreadBad() { synchronized (lockB) { } }
  @WorkerThread
  public void annotatedWorkerThreadBad() {
    synchronized (lockB) { synchronized (this) { } }
  }
  Object lockC;
  public synchronized void assertOnUIThreadBad() {
    OurThreadUtils.assertOnUiThread();
    synchronized (lockC) { }
  }
  public void assertOnBackgroundThreadBad() {
    OurThreadUtils.assertOnBackgroundThread();
    synchronized (lockC) { synchronized (this) { } }
  }
  Object lockD;
  public synchronized void notAnnotatedBadA() { synchronized (lockD) { } }
  public void notAnnotatedBBad() {
    synchronized (lockD) { synchronized (this) { } }
  }
  Object lockE, lockF, lockG;
  public void sequentialEandGOk() {
    synchronized (lockE) { synchronized (lockF) { } }
    synchronized (lockG) { }
  }
  public void nestedGthenEOk() {
    synchronized (lockG) { synchronized (lockE) { } }
  }
}