import java.util.concurrent.locks.ReentrantLock;
public class UL_UNRELEASED_LOCK_bug0 {
    ReentrantLock lock = new ReentrantLock();
    void bug(boolean b) {
        lock.lock();
        if (b)
            return;
        lock.unlock();
    }
}