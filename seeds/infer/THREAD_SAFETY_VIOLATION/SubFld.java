import javax.annotation.concurrent.ThreadSafe;
@ThreadSafe
class SuperFld {
  private int f = 0;
  public int getF() { return f; }
  protected int g = 0;
  public int getG() { return g; }
}
@ThreadSafe
class SubFld extends SuperFld {
  private int f = 0;
  public synchronized void setF() { f = 5; }
  public synchronized void setG() { g = 5; }
}