public class TLW_TWO_LOCK_WAIT_patch0 {
    Object lock = new Object();
    Object value;
    public void provideIt(Object v) {
        synchronized (lock) {
            value = v;
            lock.notifyAll();
        }
    }
    public Object waitForIt() throws InterruptedException {
        synchronized (lock) {
            while (value == null)
                lock.wait();
            return value;
        }
    }
}
