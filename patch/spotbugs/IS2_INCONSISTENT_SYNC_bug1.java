public class IS2_INCONSISTENT_SYNC_bug1 {
    int x;
    public synchronized void add1() {
        x += 1;
    }
    public int get2X() {
        return x + x;
    }
}