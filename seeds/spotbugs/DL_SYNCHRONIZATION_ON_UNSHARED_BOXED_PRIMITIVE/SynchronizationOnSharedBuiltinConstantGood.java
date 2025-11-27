import synchronizationOnSharedBuiltinConstant.*;
public class SynchronizationOnSharedBuiltinConstantGood {
    private int count = 0;
    private final Integer lock1 = new Integer(count);
    public void compliantInteger() {
        synchronized (lock1) {
            System.out.println("compliantInteger");
            count++;
        }
    }
    private final String lock2 = new String("LOCK");
    public void compliantStringInstance() {
        synchronized (lock2) { System.out.println("compliantStringInstance"); }
    }
    private final Object lock3 = new Object();
    public void compliantLockObject() {
        synchronized (lock3) { System.out.println("compliantLockObject"); }
    }
}