import codetoanalyze.java.infer.*;
import android.annotation.SuppressLint;
import com.google.common.annotations.VisibleForTesting;
import java.io.Closeable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.concurrent.GuardedBy;
class GuardedByExample {
  static class AutoCloseableReadWriteUpdateLock implements Closeable {
    @Override
    public void close() {}
  }
  private Object mLock = new Object();
  private Object mOtherLock = new Object();
  private AutoCloseableReadWriteUpdateLock mReadWriteLock = new AutoCloseableReadWriteUpdateLock();
  @GuardedBy("mLock")
  private Object f = new Object();
  @GuardedBy("this")
  Object g = new Object();
  Object mCopyOfG;
  @GuardedBy("SomeLockThatDoesntExist")
  Object h = new Object();
  @GuardedBy("mReadWriteLock")
  Object i = new Object();
  private static Object sLock = new Object();
  @GuardedBy("sLock")
  static Object sFld;
  @GuardedBy("GuardedByExample.class")
  static Object sGuardedByClass;
  static { sFld = new Object(); }
  public GuardedByExample() {
    f.toString();
    g = new Object();
  }
  void readFBad() { this.f.toString(); }
  @SuppressLint("InvalidAccessToGuardedField")
  void readFBadButSuppressed() { this.f.toString(); }
  @SuppressLint("SomeOtherWarning")
  void readFBadButSuppressedOther() { this.f.toString(); }
  void writeFBad() { this.f = new Object(); }
  void readFBadWrongLock() { synchronized (mOtherLock) { this.f.toString(); } }
  void writeFBadWrongLock() { synchronized (mOtherLock) { this.f = new Object(); } }
  void readFAfterBlockBad() {
    synchronized (mLock) { }
    this.f.toString();
  }
  void writeFAfterBlockBad() {
    synchronized (mLock) { }
    this.f = new Object();
  }
  @GuardedBy("mOtherLock")
  void readFBadWrongAnnotation() { this.f.toString(); }
  @GuardedBy("mLock")
  void readFOkMethodAnnotated() { this.f.toString(); }
  synchronized void synchronizedMethodReadOk() { this.g.toString(); }
  synchronized void synchronizedMethodWriteOk() { this.g = new Object(); }
  void readFOkSynchronized() { synchronized (mLock) { this.f.toString(); } }
  void writeFOkSynchronized() { synchronized (mLock) { this.f = new Object(); } }
  synchronized void synchronizedMethodReadBad() { this.f.toString(); }
  synchronized void synchronizedMethodWriteBad() { this.f = new Object(); }
  void reassignCopyOk() {
    synchronized (this) { mCopyOfG = g; }
    mCopyOfG = new Object(); 
  }
  void readHBad() { synchronized (mLock) { this.h.toString(); } }
  synchronized void readHBadSynchronizedMethodShouldntHelp() { this.h.toString(); }
  private void privateUnguardedAccess() { this.g.toString(); }
  public void guardedCallSite1() { synchronized (this) { privateUnguardedAccess(); } }
  public synchronized void guardedCallSite2() { privateUnguardedAccess(); }
  private void wrapper() { privateUnguardedAccess(); }
  public void guardedCallSite3() { synchronized (this) { wrapper(); } }
  void readWriteLockOk() { try (AutoCloseableReadWriteUpdateLock lock = mReadWriteLock) { this.i.toString(); } }
  static synchronized void staticSynchronizedOk() { sGuardedByClass.toString(); }
  static void synchronizeOnClassOk1() {
    synchronized (GuardedByExample.class) {
      sGuardedByClass.toString(); 
      sGuardedByClass = new Object(); 
    }
  }
  void synchronizedOnThisBad() { sGuardedByClass.toString(); }
  Object dontReportOnCompilerGenerated() {
    return new Object() {
      public void accessInAnonClassOk() { synchronized (mLock) { f.toString(); } }
    };
  }
  Object readFromInnerClassOkOuter() {
    return new Object() {
      public String readFromInnerClassOk() { synchronized (GuardedByExample.this) { return g.toString(); } }
    };
  }
  Object readFromInnerClassBad1Outer() {
    return new Object() {
      public String readFromInnerClassBad1() { synchronized (this) { return g.toString(); } }
    };
  }
  Object readFromInnerClassBad2Outer() {
    return new Object() { public synchronized String readFromInnerClassBad2() { return g.toString(); } };
  }
  @VisibleForTesting
  public void visibleForTestingOk1() { f.toString(); }
  @VisibleForTesting
  void visibleForTestingOk2() { f.toString(); }
  synchronized Object returnPtG() { return g; }
  void readGFromCopyOk() {
    synchronized (this) {
      mCopyOfG = g; 
      g.toString();
    }
    mCopyOfG.toString(); 
  }
  void usePtG() {
    Object ptG = returnPtG();
    ptG.toString();
  }
  Object byRefTrickyBad() {
    Object local = null;
    synchronized (this) { local = g; }
    g.toString(); 
    return local;
  }
  void byRefTrickyOk() {
    Object local = null;
    synchronized (this) { local = g; }
    local.toString(); 
  }
  @GuardedBy("ui_thread")
  Object uiThread1;
  @GuardedBy("ui-thread")
  Object uiThread2;
  @GuardedBy("uithread")
  Object uiThread3;
  @GuardedBy("something that's clearly not an expression")
  Object nonExpression;
  void accessUnrecognizedGuardedByFieldsOk() {
    uiThread1 = new Object();
    uiThread1.toString();
    uiThread2 = new Object();
    uiThread2.toString();
    uiThread3 = new Object();
    uiThread3.toString();
    nonExpression = new Object();
    nonExpression.toString();
  }
  @GuardedBy("GuardedByExample.this")
  Object guardedByOuterThis;
  synchronized void okOuterAccess() { guardedByOuterThis = null; }
  private class Inner {
    @GuardedBy("this")
    Object guardedByInnerThis1;
    @GuardedBy("Inner.this")
    Object guardedByInnerThis2;
    @GuardedBy("GuardedByExample$Inner.this")
    Object guardedByInnerThis3;
    @GuardedBy("Inner.class")
    Object guardedByInnerClass1;
    @GuardedBy("GuardedByExample.Inner.class")
    Object guardedByInnerClass2;
    @GuardedBy("GuardedByExample$Inner.class")
    Object guardedByInnerClass3;
    synchronized void okAccess1() { guardedByInnerThis1 = null; }
    synchronized void okAccess2() { guardedByInnerThis2 = null; }
    synchronized void okAccess3() { guardedByInnerThis3 = null; }
    void okInnerClassGuard1() {
      synchronized (Inner.class) {
        guardedByInnerClass1 = new Object();
        guardedByInnerClass2 = new Object();
        guardedByInnerClass3 = new Object();
      }
    }
    void okInnerClassGuard2() {
      synchronized (GuardedByExample.Inner.class) {
        guardedByInnerClass1 = new Object();
        guardedByInnerClass2 = new Object();
        guardedByInnerClass3 = new Object();
      }
    }
  }
  int n;
  public void withloop2() {
    synchronized (mLock) { for (int i = 0; i <= n; i++) { f = 42; } }
  }
  public void withoutloop2() { synchronized (mLock) { f = 42; } }
  @GuardedBy("self_reference")
  Object self_reference;
  void guardedBySelfReferenceOK() { synchronized (self_reference) { this.self_reference.toString(); } }
  @GuardedBy("itself")
  Object itself_fld;
  void itselfOK() { synchronized (itself_fld) { this.itself_fld.toString(); } }
  ReadWriteLock mRWL;
  @GuardedBy("mRWL")
  Integer guardedbymRWL;
  Integer someOtherInt;
  void readLockOK() {
    mRWL.readLock().lock();
    someOtherInt = guardedbymRWL;
    mRWL.readLock().unlock();
  }
  void writeLockOK() {
    mRWL.writeLock().lock();
    guardedbymRWL = 55;
    mRWL.writeLock().unlock();
  }
  ReentrantReadWriteLock mRRWL;
  @GuardedBy("mRRWL")
  Integer guardedbymRRWL;
  void reentrantReadLockOK() {
    mRRWL.readLock().lock();
    someOtherInt = guardedbymRRWL;
    mRRWL.readLock().unlock();
  }
  void reentrantWriteLockOK() {
    mRRWL.writeLock().lock();
    guardedbymRRWL = 55;
    mRRWL.writeLock().unlock();
  }
  @GuardedBy("this")
  Integer xForSub;
  static class Sub extends GuardedByExample {
    void goodSub1() { synchronized (this) { xForSub = 22; } }
    synchronized void goodSub2() { xForSub = 22; }
    void badSub() { xForSub = 22; }
  }
  Lock normallock;
  @GuardedBy("normallock")
  Integer guardedbynl;
  ReentrantLock reentrantlock;
  @GuardedBy("reentrantlock")
  Integer guardedbyrel;
  void goodGuardedByNormalLock() {
    normallock.lock();
    guardedbynl = 22;
    normallock.unlock();
  }
  void goodTryLockGuardedByNormalLock() {
    if (normallock.tryLock()) {
      guardedbynl = 22;
      normallock.unlock();
    }
  }
  void goodTryLockGuardedByReentrantLock() {
    if (reentrantlock.tryLock()) {
      guardedbyrel = 44;
      reentrantlock.unlock();
    }
  }
  void badGuardedByNormalLock() { guardedbynl = 22; }
  void badGuardedByReentrantLock() { guardedbyrel = 44; }
  static class OtherClassWithLock {
    ReentrantLock lock;
    @GuardedBy("lock")
    Object guardedByLock;
    Object otherClassObject;
    void guardedInSameClassOk() {
      lock.lock();
      guardedByLock = new Object();
      lock.unlock();
    }
  }
  @GuardedBy("OtherClassWithLock.lock")
  Object guardedByLock1;
  @GuardedBy("codetoanalyze.java.infer.GuardedByExample$OtherClassWithLock.lock")
  Object guardedByLock2;
  @GuardedBy("OtherClassWithLock.otherClassObject")
  Object guardedByLock3;
  OtherClassWithLock otherClass;
  void guardedByTypeSyntaxOk1() {
    otherClass.lock.lock();
    guardedByLock1 = true;
    guardedByLock2 = true;
    otherClass.lock.unlock();
  }
  void guardedByTypeSyntaxOk2() { synchronized (otherClass.otherClassObject) { guardedByLock3 = true; } }
  void guardedByTypeSyntaxBad() {
    guardedByLock1 = true;
    guardedByLock2 = true;
  }
}