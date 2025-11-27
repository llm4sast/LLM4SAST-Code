import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class JLM_JSR166_LOCK_MONITORENTER_bug0 {
    Lock lock = new ReentrantLock();
    int value;
    public int nextValue() {
        synchronized (lock) { return value++; }
    }
}