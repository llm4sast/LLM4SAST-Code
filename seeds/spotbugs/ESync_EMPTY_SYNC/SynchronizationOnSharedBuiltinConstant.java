import foundOnTheWeb.*;
public class SynchronizationOnSharedBuiltinConstant {
    static private final String LOCK = "LOCK";
    void someMethod() {
        synchronized (LOCK) { }
    }
}