import com.facebook.infer.annotation.Lockless;
class LocklessTests {}
interface Listener {
  @Lockless
  void locklessMethod();
  void normalMethod();
}
class LocklessTestsA implements Listener {
  @Override
  public void locklessMethod() { synchronized (this) { } }
  @Override
  public void normalMethod() { synchronized (this) { } }
}
class LocklessTestsB implements Listener {
  @Lockless
  @Override
  public synchronized void locklessMethod() {}
  @Override
  public synchronized void normalMethod() {}
}
class LocklessTestsC implements Listener {
  private synchronized void takeLock() {}
  @Override
  public void locklessMethod() { takeLock(); }
  @Override
  public synchronized void normalMethod() {}
}