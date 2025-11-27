import java.util.concurrent.locks.ReentrantLock;
public class UL_UNRELEASED_LOCK_patch0 {
    ReentrantLock lock = new ReentrantLock();
    void notBug(boolean b) {
        lock.lock();
        try {
            if (b)
                return;
        } finally {
            lock.unlock();
        }
    }
}