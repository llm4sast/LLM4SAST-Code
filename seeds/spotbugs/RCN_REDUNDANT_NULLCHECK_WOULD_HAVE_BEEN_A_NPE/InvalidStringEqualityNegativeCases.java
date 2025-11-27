import com.google.errorprone.bugpatterns.*;
public class InvalidStringEqualityNegativeCases {
  public boolean testEquality(String x, String y) {
    boolean retVal;
    retVal = x.equals(y);
    retVal = (x == null);
    retVal = (x != null);
    retVal = (null == x);
    retVal = (null != x);
    return retVal;
  }
  @SuppressWarnings("StringEquality")
  public boolean testSuppressWarnings(String x, String y) {
    boolean retVal;
    retVal = (x != y);
    retVal = (x == y);
    return retVal;
  }
}