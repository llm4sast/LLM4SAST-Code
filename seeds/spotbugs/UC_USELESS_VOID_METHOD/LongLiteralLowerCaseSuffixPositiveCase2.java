import com.google.errorprone.bugpatterns.*;
public class LongLiteralLowerCaseSuffixPositiveCase2 {
  @SuppressWarnings("unused")
  private static final String TEST_STRING = "Îñţérñåţîöñåļîžåţîờñ";
  public void underscoredLowerCase() { long value = 0_1__2l; }
}