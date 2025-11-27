import com.google.errorprone.bugpatterns.*;
import java.util.regex.Pattern;
public class InvalidPatternSyntaxPositiveCases {
  public static final String INVALID = "*";
  public static final String DOT = "."; {
    Pattern.compile(INVALID);
    Pattern.compile(INVALID, 0);
    Pattern.matches(INVALID, "");
    "".matches(INVALID);
    "".replaceAll(INVALID, "");
    "".replaceFirst(INVALID, "");
    "".split(INVALID);
    "".split(INVALID, 0);
    "foo.bar".split(".");
    "foo.bonk".split(DOT);
  }
}