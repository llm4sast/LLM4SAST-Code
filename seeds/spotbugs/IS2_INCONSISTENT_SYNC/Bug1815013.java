import sfBugs.*;
public class Bug1815013 {
    private int x, y;
    public Bug1815013(Bug1815013 p) { this(p.x, p.y); }
    public Bug1815013(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public synchronized int[] get() { return new int[] { x, y }; }
    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void set2(Bug1815013 p) {
        synchronized (this) { p.x = y; }
    }
    public void set3(Bug1815013 p) {
        synchronized (this) { p.x = get()[0]; }
    }
    public void set4(Bug1815013 p) {
        synchronized (p) { p.x = get()[0]; }
    }
    public void set5(Bug1815013 p) {
        synchronized (p) {
            synchronized (this) { p.x = y; }
        }
    }
    private void privateUnsynchSetThis(Bug1815013 p) { this.x = p.get()[0]; }
    public void publicSyncThis(Bug1815013 p) {
        synchronized (this) { privateUnsynchSetThis(p); }
    }
    private void privateUnsynchSetP(Bug1815013 p) { p.x = get()[0]; }
    public void publicSyncP(Bug1815013 p) {
        synchronized (p) { privateUnsynchSetP(p); }
    }
}