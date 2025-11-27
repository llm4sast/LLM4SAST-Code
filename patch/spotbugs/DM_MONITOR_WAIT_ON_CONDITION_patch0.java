import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
public class DM_MONITOR_WAIT_ON_CONDITION_patch0 {
    public static void test(Condition c) throws InterruptedException {
        c.await();
    }
}