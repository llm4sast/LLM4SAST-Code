import com.google.errorprone.bugpatterns.*;
public class InvalidStringEqualityPositiveCases {
  public boolean testEquality(String x, String y) {
    boolean retVal;
    retVal = (x == y);
    retVal = (x != y);
    retVal = (x + y == y + x);
    retVal = (x + y != y + x);
    retVal = (x + "str" == y + "str");
    retVal = (x + "str" != y + "str");
    retVal = ("str" == x);
    retVal = (x == "str") ;
    retVal = ("str2" == "str");
    final String constValue = "str";
    retVal = (x == constValue);
    retVal = (x != constValue);
    retVal = (x + y + constValue == x + y);
    final String constValue2 = "str2";
    retVal = (constValue + constValue2 == x);
    retVal = (x == constValue + constValue2);
    return retVal;
  }
}