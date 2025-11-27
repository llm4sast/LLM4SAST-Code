import sfBugs.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
public class Bug2898106 {
    private static final ConcurrentHashMap<String, Semaphore> locks = new ConcurrentHashMap<String, Semaphore>();
    static Semaphore getLock(String key) {
        Semaphore lock = locks.get(key);
        if (lock == null) {
            Semaphore newLock = new Semaphore(1, true);
            lock = locks.putIfAbsent(key, lock);
            if (lock == null)
                lock = newLock;
        }
        return lock;
    }
}