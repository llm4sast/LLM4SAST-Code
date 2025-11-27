import singletons.*;
public class DoubleCheckedLockingIndirect {
    private static DoubleCheckedLockingIndirect instance;
    private DoubleCheckedLockingIndirect() { }
    private static synchronized void init() {
        if (instance == null)
            instance = new DoubleCheckedLockingIndirect();
    }
    public static DoubleCheckedLockingIndirect getInstance() {
        if (instance == null)
            init();
        return instance;
    }
}