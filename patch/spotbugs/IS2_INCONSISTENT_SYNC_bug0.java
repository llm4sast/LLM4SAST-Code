public class IS2_INCONSISTENT_SYNC_bug0 {
    int x;
    public int getX() {
        return x;
    }
    public synchronized void setX(int x) {
        this.x = x;
    }
    public synchronized int calculate() {
        int t = 0;
        t += x;
        t += x;
        return t;
    }
}