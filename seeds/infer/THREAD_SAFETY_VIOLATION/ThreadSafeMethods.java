import codetoanalyze.java.checkers.*;
import com.facebook.infer.annotation.ThreadSafe;
import com.google.common.annotations.VisibleForTesting;
class ThreadSafeMethods {
  Object field1;
  Object field2;
  Object field3;
  Object field4;
  Object field5;
  @ThreadSafe
  public void threadSafeMethodWriteBad() { this.field1 = new Object(); }
  @ThreadSafe
  public Object threadSafeMethodReadBad() { return this.field2; }
  @ThreadSafe
  private void threadSafePrivateMethodBad() { this.field2 = new Object(); }
  @ThreadSafe
  @VisibleForTesting
  public void threadSafeVisibleForTestingMethodBad() { this.field3 = new Object(); }
  @ThreadSafe
  public void safeMethodOverride() {}
  public void writeSameFieldAsThreadSafeMethod1Bad() { this.field1 = new Object(); }
  public Object readSameFieldAsThreadSafeMethod1Bad() { return this.field1; }
  public synchronized void safelyWriteSameFieldAsThreadSafeMethod1Ok() { this.field1 = new Object(); }
  public synchronized Object readSameFieldAsThreadSafeMethodWhileSynchronized1Bad() { return this.field1; }
  @ThreadSafe
  public synchronized void synchronizedWriteOk() { this.field4 = new Object(); }
  public void writeSameFieldAsThreadSafeMethod2Bad() { this.field4 = new Object(); }
  public Object readSameFieldAsThreadSafeMethod2Bad() { return this.field4; }
  @ThreadSafe
  public synchronized Object FN_synchronizedReadBad() { return this.field5; }
  private void privateAccessOk() { this.field5 = new Object(); }
  public void FN_writeSameFieldAsThreadSafeMethod3Bad() { this.field5 = new Object(); }
  public Object readSameFieldAsThreadSafeMethodOk() { return this.field5; }
}
class ThreadSafeMethodsSubclass extends ThreadSafeMethods {
  Object subclassField;
  @Override
  public void safeMethodOverride() { this.subclassField = new Object(); }
  public void FN_writeThreadSafeFieldOfSuperclassBad() { this.field1 = new Object(); }
  public Object FN_readThreadSafeFieldOfSuperclassBad() { return this.field1; }
  public void writeThreadSafeFieldOfOverrideBad() { this.subclassField = new Object(); }
  public Object readThreadSafeFieldOfOverrideBad() { return this.subclassField; }
}