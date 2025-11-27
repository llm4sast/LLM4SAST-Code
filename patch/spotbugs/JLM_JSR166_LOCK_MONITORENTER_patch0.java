import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class JLM_JSR166_LOCK_MONITORENTER_patch0 {
    Lock lock = new ReentrantLock();
    int value;
    public int nextValue() {
        lock.lock();
        try {
            return value++;
        } finally {
          lock.unlock();
        }
    }
}