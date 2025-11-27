import android.support.annotation.UiThread;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
class Dedup {
  CountDownLatch latch;
  Future future;
  @UiThread
  void onUiThreadBad() throws InterruptedException, ExecutionException { callMethodWithMultipleBlocksBad(); }
  @UiThread
  void callMethodWithMultipleBlocksBad() throws InterruptedException, ExecutionException {
    future.get();
    latch.await();
    future.get();
  }
  Object lockA, lockB;
  void oneWayBad() {
    synchronized (lockA) { synchronized (lockB) { } } // DEADLOCK 1
  }
  void anotherWayBad() {
    synchronized (lockB) { synchronized (lockA) { } }
  }
  void thirdLongerWayBad() { anotherWayBad(); }
}