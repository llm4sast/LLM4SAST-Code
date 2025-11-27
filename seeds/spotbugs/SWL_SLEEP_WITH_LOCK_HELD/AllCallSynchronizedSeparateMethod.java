import multithreaded.primitivewrite.*;
public class AllCallSynchronizedSeparateMethod implements Runnable {
    private boolean done = false;
    @Override
    public void run() {
        synchronized (AllCallSynchronizedSeparateMethod.class) {
            while (!isDone()) {
                try {
                    Thread.sleep(1000); 
                } catch(InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
    }
    private boolean isDone() { return done; }
    private void shutdown() { done = true; }
    public synchronized void synchronizedShutdown() { shutdown(); }
}