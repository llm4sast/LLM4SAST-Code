import com.google.errorprone.bugpatterns.*;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Preconditions;
public class PreconditionsCheckNotNullPrimitivePositiveCases {
  private Tester field = new Tester();
  public void test() {
    Object a = new Object();
    Object b = new Object();
    byte byte1 = 0;
    short short1 = 0;
    int int1 = 0, int2 = 0;
    long long1 = 0;
    float float1 = 0;
    double double1 = 0;
    boolean boolean1 = false, boolean2 = false;
    char char1 = 0;
    Tester tester = new Tester();
    Preconditions.checkNotNull(byte1);
    Preconditions.checkNotNull(short1);
    Preconditions.checkNotNull(int1);
    Preconditions.checkNotNull(long1);
    Preconditions.checkNotNull(float1);
    Preconditions.checkNotNull(double1);
    Preconditions.checkNotNull(boolean1);
    Preconditions.checkNotNull(char1);
    boolean1 = Preconditions.checkNotNull(boolean2);
    boolean1 = Preconditions.checkNotNull(int1 == int2);
    Preconditions.checkNotNull(tester.hasId());
    Preconditions.checkNotNull(tester.hasId(), "Must have ID!");
    Preconditions.checkNotNull(tester.hasId(), "Must have %s!", "ID");
    Preconditions.checkNotNull(a != null);
    Preconditions.checkNotNull(a == null);
    Preconditions.checkNotNull(int1 == int2);
    Preconditions.checkNotNull(int1 > int2);
    Preconditions.checkNotNull(boolean1 ? int1 : int2);
    checkNotNull(byte1);
    checkNotNull(tester.hasId());
  }
  public void test2(Tester arg) {
    Tester local = new Tester();
    checkNotNull(arg.hasId());
    checkNotNull(field.hasId());
    checkNotNull(local.hasId());
    checkNotNull(!local.hasId());
    checkNotNull(!(arg instanceof Tester));
    checkNotNull(arg.getId());
    int id = checkNotNull(arg.getId());
    boolean b = checkNotNull(arg.hasId());
    checkNotNull(arg.getTester().getTester().hasId());
    checkNotNull(arg.tester.getTester().hasId());
  }
  private static class Tester {
    public Tester tester;
    public boolean hasId() { return true; }
    public int getId() { return 10; }
    public Tester getTester() { return tester; }
  }
}