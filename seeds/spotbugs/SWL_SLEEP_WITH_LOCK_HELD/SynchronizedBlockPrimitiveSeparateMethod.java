import multithreaded.primitivewrite.*;
public class SynchronizedBlockPrimitiveSeparateMethod implements Runnable {
    private boolean done = false;
    @Override
    public void run() {
        synchronized (SynchronizedBlockPrimitiveSeparateMethod.class) {
            while (!isDone()) {
                try {
                    Thread.sleep(1000); 
                } catch(InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
    }
    private boolean isDone() { return done; }
    public void shutdown() {
        synchronized (SynchronizedBlockPrimitiveSeparateMethod.class) { done = true; }
    }
}