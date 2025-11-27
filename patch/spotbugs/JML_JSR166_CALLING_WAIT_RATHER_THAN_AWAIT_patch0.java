import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
public class JML_JSR166_CALLING_WAIT_RATHER_THAN_AWAIT_patch0 {
    public static void test(Condition c) throws InterruptedException {
        c.await();
        c.signal();
        c.signalAll();
    }
}