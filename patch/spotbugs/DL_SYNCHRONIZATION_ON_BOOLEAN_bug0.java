public class DL_SYNCHRONIZATION_ON_BOOLEAN_bug0 {
    private final Boolean initialized = Boolean.FALSE;
    public void doSomething() {
        synchronized (initialized) {
            System.out.println("noncompliantBooleanLockObject");
        }
    }
}