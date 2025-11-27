import codetoanalyze.java.checkers.*;
import com.facebook.infer.annotation.ThreadSafe;
import com.google.common.annotations.VisibleForTesting;
import javax.annotation.concurrent.NotThreadSafe;

@ThreadSafe
class ThreadSafeExample {
  static Class<?> A = ThreadSafeExample.class;
  Integer f;

  public ThreadSafeExample() {
    f = 86;
  }

  public void tsOK() {
    synchronized (this) {
      f = 42;
    }
  }

  public void tsBad() {
    f = 24;
  }

  public void recursiveBad() {
    f = 44; // TSV 2
    recursiveBad();
  }

  private void assignInPrivateMethodOk() {
    f = 24;
  }

  public void callPublicMethodBad() {
    assignInPrivateMethodOk();
  }

  private void callAssignInPrivateMethod() {
    assignInPrivateMethodOk();
  }

  public void deeperTraceBad() {
    callAssignInPrivateMethod();
  }

  public synchronized void callFromSynchronizedPublicMethodOk() {
    assignInPrivateMethodOk();
  }

  private synchronized void synchronizedCallerOk() {
    assignInPrivateMethodOk();
  }

  public void callFromUnsynchronizedPublicMethodOk() {
    synchronizedCallerOk();
  }

  public void callConstructorOk() {
    new ThreadSafeExample();
  }

  private Object returnConstructorOk() {
    return new ThreadSafeExample();
  }

  public void transitivelyCallConstructorOk() {
    returnConstructorOk();
  }

  volatile Object volatileField;

  public void unsafeVolatileWriteOk() {
    this.volatileField = new Object();
  }

  @VisibleForTesting
  public void visibleForTestingNotPublicOk() {
    this.f = 47;
  }

  public void callVisibleForTestingBad() {
    visibleForTestingNotPublicOk();
  }

  Object sharedField;

  private void writePrivateSharedFieldOk() {
    this.sharedField = new Object();
  }

  public Object returnSharedFieldOk() {
    return this.sharedField;
  }

  Object sStaticField;

  public Object FP_lazyInitOk() {
    synchronized (ThreadSafeExample.class) {
      if (sStaticField != null) {
        sStaticField = new Object();
      }
    }
    return sStaticField;
  }
}

class ExtendsThreadSafeExample extends ThreadSafeExample {
  Integer field;

  public void newmethodBad() {
    field = 22;
  }

  public void tsOK() {
    field = 44;
  }
}

@NotThreadSafe
class NotThreadSafeExtendsThreadSafeExample extends ThreadSafeExample {
  Integer field;

  public void newmethodBad() {
    field = 22;
  }
}

@ThreadSafe
class YesThreadSafeExtendsNotThreadSafeExample extends NotThreadSafeExtendsThreadSafeExample {
  Integer subsubfield;

  public void subsubmethodBad() {
    subsubfield = 22;
  }
}

class Unannotated {
  int mField;

  void callThreadSafeAnnotatedCode1Ok(ThreadSafeExample o) {
    o.f = null;
  }

  void callThreadSafeAnnotatedCode2Ok(ThreadSafeExample o) {
    o.tsBad();
  }

  void mutateMyFieldOk() {
    this.mField = 1;
  }
}