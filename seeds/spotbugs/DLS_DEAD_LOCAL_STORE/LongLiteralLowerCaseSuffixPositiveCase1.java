import com.google.errorprone.bugpatterns.*;
public class LongLiteralLowerCaseSuffixPositiveCase1 {
  @SuppressWarnings("unused")
  private static final String TEST_STRING = "Îñţérñåţîöñåļîžåţîờñ";
  public void positiveLowerCase() { long value = 123432l; }
  public void zeroLowerCase() { long value = 0l; }
  public void negativeLowerCase() { long value = -123432l; }
  public void negativeExtraSpacesLowerCase() { long value = -  123432l; }
  public void positiveHexLowerCase() {
    long value = 0x8abcDEF0l;
    value = 0X80l;
  }
  public void zeroHexLowerCase() {
    long value = 0x0l;
    value = 0X0l;
  }
  public void negativeHexLowerCase() {
    long value = -0x8abcDEF0l;
    value = -0X80l;
  }
  public void negativeHexExtraSpacesLowerCase() { long value = -  0x8abcDEF0l; }
  public void positiveOctalLowerCase() { long value = 06543l; }
  public void zeroOctalLowerCase() { long value = 00l; }
  public void negativeOctalLowerCase() { long value = -06543l; }
  public void negativeOctalExtraSpacesLowerCase() { long value = -  06543l; }
}