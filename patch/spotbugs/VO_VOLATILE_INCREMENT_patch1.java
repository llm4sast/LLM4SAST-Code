import java.util.concurrent.locks.ReentrantLock;
public class VO_VOLATILE_INCREMENT_patch1 {
    private volatile int x = 0;
    private ReentrantLock l = new ReentrantLock();
    public void testB() {
        l.lock();
        try {
            x++;
        } finally {
            l.unlock();
        }
    }
}
