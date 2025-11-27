import com.google.errorprone.bugpatterns.*;
public class LongLiteralLowerCaseSuffixNegativeCases {
  public void positiveUpperCase() { long value = 123432L; }
  public void zeroUpperCase() { long value = 0L; }
  public void negativeUpperCase() { long value = -3L; }
  public void notLong() { String value = "0l"; }
  public void variableEndingInEllIsNotALongLiteral() {
    long ell = 0L;
    long value = ell;
  }
  public void positiveNoSuffix() { long value = 3; }
  public void negativeNoSuffix() { long value = -3; }
  public void positiveHexUpperCase() { long value = 0x80L; }
  public void zeroHexUpperCase() { long value = 0x0L; }
  public void negativeHexUpperCase() { long value = -0x80L; }
}