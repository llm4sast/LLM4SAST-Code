public class ML_SYNC_ON_FIELD_TO_GUARD_CHANGING_THAT_FIELD_bug0 {
    public ML_SYNC_ON_FIELD_TO_GUARD_CHANGING_THAT_FIELD_bug0(int y, int z) {
        this.y = y;
        this.z = z;
    }
    Integer x = new Integer(0);
    int y;
    int z;
    public void doSomething() {
        synchronized (x) {
            x++;
            y++;
            z++;
        }
    }
}