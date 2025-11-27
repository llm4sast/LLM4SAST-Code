public class DL_SYNCHRONIZATION_ON_BOOLEAN_patch0 {
    private final Object initialized = new Object();
    public void doSomething() {
        synchronized (initialized) {
            System.out.println("noncompliantBooleanLockObject");
        }
    }
}