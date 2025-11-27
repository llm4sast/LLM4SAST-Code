import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import java.util.regex.Pattern;
class Regexp {
  void potentiallyCostly() {
    Pattern.compile("a regexp");
    int flags = 0;
    Pattern.compile("a regexp", flags);
    Pattern.matches("a regexp", "a CharSequence");
  }
  @UiThread
  void annotatedBad() { potentiallyCostly(); }
  void noThreadOk() { potentiallyCostly(); }
  @WorkerThread
  void workerThreadOk() { potentiallyCostly(); }
  void assertedBad() {
    OurThreadUtils.assertMainThread();
    potentiallyCostly();
  }
}