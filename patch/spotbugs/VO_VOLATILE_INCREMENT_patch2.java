import java.util.concurrent.locks.ReentrantLock;
public class VO_VOLATILE_INCREMENT_patch2 {
    private volatile int x = 0;
    private ReentrantLock l = new ReentrantLock();
    public void testC() {
        Foo f = new Foo();
        f.lock();
        try {
            f.x++;
        } finally {
            f.unlock();
        }
    }
    private class Foo extends ReentrantLock {
        volatile int x = 0;
    }
}
