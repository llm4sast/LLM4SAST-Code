public class MismatchedWaitFalsePositive {
    Object lock;
    boolean ready = false;
    MismatchedWaitFalsePositive(Object x) { lock = x; }
    public void waitOnLock() {
        synchronized (lock) {
            while (!ready) {
                try {
                    lock.wait();
                    return;
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
    public void notifyAllOnLock() {
        synchronized (lock) {
            ready = true;
            lock.notify();
        }
    }
}