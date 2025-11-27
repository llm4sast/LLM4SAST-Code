public class TLW_TWO_LOCK_WAIT_bug0 {
    Object lock = new Object();
    Object value;
    public synchronized void provideIt(Object v) {
        synchronized (lock) {
            value = v;
            lock.notifyAll();
        }
    }
    public synchronized Object waitForIt() throws InterruptedException {
        synchronized (lock) {
            while (value == null)
                lock.wait();
            return value;
        }
    }
}
