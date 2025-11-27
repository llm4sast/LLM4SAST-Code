import sfBugs.*;
import edu.umd.cs.findbugs.annotations.DesireNoWarning;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug3368029 {
    @NoWarning("IS")
    private int x;
    public synchronized int getX() { return x; }
    @NoWarning("IS")
    public void setX(int x) {
        synchronized (this) { this.x = x; }
    }
    @ExpectWarning("IS")
    private int total;
    public synchronized int getTotal() { return total; }
    public void foobar() { this.total = 0; }
    public void count(String... things) {
        for (String thing : things) { countOne(thing); }
    }
    private synchronized void countOne(String thing) { total += thing.length(); }
    @DesireNoWarning("IS")
    private int total2;
    public synchronized int count2(String... things) {
        this.total2 = 0;
        for (final String thing : things) {
            new Runnable() {
                @Override
				public void run() { countOne2(thing); }
            }.run();
        }
        return total2;
    }
    @DesireNoWarning("IS")
    private void countOne2(String thing) {
        assert Thread.holdsLock(this);
        total2 += thing.length();
    }
}