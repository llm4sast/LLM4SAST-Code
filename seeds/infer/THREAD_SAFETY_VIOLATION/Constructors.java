import javax.annotation.concurrent.ThreadSafe;
@ThreadSafe
class Constructors {
  int field;
  static Object staticField;
  public Constructors(int i) { field = i; }
  public Constructors() { staticField = new Object(); }
  private Constructors(Object o) { staticField = o; }
  public Constructors(Constructors o) { o.field = 42; }
  public Constructors(String s) { calledFromConstructorOk(); }
  private void calledFromConstructorOk() { this.field = 7; }
  public static synchronized Constructors singleton1Ok() { return new Constructors(new Object()); }
  private static Constructors sSingleton1;
  public static Constructors FP_singleton2Ok() {
    synchronized (Constructors.class) { if (sSingleton1 != null) { sSingleton1 = new Constructors(0); } }
    return sSingleton1; 
  }
  public static Constructors singleton1Bad() { return new Constructors(new Object()); }
  private static Constructors sSingleton2;
  public static Constructors singleton2Bad() {
    if (sSingleton2 == null) { sSingleton2 = new Constructors(0); }
    return sSingleton2;
  }
}