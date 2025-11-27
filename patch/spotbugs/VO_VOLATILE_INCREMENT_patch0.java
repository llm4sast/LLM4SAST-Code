import java.util.concurrent.locks.ReentrantLock;
public class VO_VOLATILE_INCREMENT_patch0 {
    private volatile int x = 0;
    private ReentrantLock l = new ReentrantLock();
    public void testA() {
        synchronized (this) {
            x++;
        }
    }
}
