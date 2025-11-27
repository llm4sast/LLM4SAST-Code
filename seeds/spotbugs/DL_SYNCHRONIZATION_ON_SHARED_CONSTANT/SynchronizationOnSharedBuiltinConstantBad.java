import synchronizationOnSharedBuiltinConstant.*;
public class SynchronizationOnSharedBuiltinConstantBad {
    private final Boolean initialized = Boolean.FALSE;
    public void noncompliantBooleanLockObject() {
        synchronized (initialized) { System.out.println("noncompliantBooleanLockObject"); }
    }
    private int count = 0;
    private final Integer lock1 = count;
    public void noncompliantBoxedPrimitive() {
        synchronized (lock1) {
            System.out.println("noncompliantBoxedPrimitive");
            count++;
        }
    }
    final String lock2 = new String("LOCK").intern();
    public void noncompliantInternedStringObject() {
        synchronized (lock2) { System.out.println("noncompliantInternedStringObject"); }
    }
    private final String lock3 = "LOCK";
    public void noncompliantStringLiteral() {
        synchronized (lock3) { System.out.println("noncompliantStringLiteral"); }
    }
}