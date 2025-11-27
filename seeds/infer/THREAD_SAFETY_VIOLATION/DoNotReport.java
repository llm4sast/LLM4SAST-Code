import com.facebook.infer.annotation.ThreadSafe;
@ThreadSafe
class DoNotReport {
  int mFld;
  void obviousRaceBad(int i) { mFld = i; }
}