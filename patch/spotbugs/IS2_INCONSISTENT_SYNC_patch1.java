public class IS2_INCONSISTENT_SYNC_patch1 {
    int x;
    Object lock = new Object();
    public void add1() {
        synchronized (lock) {
            x += 1;
        }
    }
    public int get2X() {
        return x + x;
    }
}