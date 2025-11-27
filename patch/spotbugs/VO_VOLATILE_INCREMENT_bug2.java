import java.util.concurrent.locks.ReentrantLock;
public class VO_VOLATILE_INCREMENT_bug2 {
    private volatile int x = 0;
    public void testA() {
        x++;
    }
}
