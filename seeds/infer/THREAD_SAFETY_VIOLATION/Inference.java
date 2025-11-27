import codetoanalyze.java.checkers.*;
class Inference {
  int mField1;
  synchronized void writeUnderLock1Ok() { mField1 = 1; }
  int unprotectedRead1Bad() {
    int ret = mField1;
    return ret;
  }
  int mField2;
  void writeUnderLock2Ok() { synchronized (this) { mField2 = 2; } }
  int unprotectedRead2Bad() {
    int ret = mField2;
    return ret;
  }
  int mField3;
  private synchronized void useLock() {}
  int useLockInCalleeThenReadBad() {
    useLock();
    return mField3;
  }
  void FN_writeToFieldWrittenInLockUsingMethodBad() { mField3 = 3; }
  int mField4;
  int mField5;
  synchronized int readInsideSyncCoincidentally() {
    mField4 = 4; 
    int ret = mField5; 
    return ret;
  }
  int read4OutsideSyncBad() {
    int ret = mField4; 
    return ret;
  }
  void write5OutsideSyncOk() { mField5 = 5; }
}