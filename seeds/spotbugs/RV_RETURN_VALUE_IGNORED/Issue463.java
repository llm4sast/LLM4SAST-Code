import ghIssues.issue463.*;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
public class Issue463 {
    private String value;
    String getValue() { return value; }
    @CanIgnoreReturnValue
    String returnAValue(String newValue) {
        value = newValue;
        return newValue;
    }
    String returnAnotherValue(String newValue) {
        value = newValue;
        return newValue;
    }
    public static String testNoError() {
        Issue463 i = new Issue463();
        i.returnAValue("foobar");
        return i.getValue();
    }
    public static String testWithError() {
        Issue463 i = new Issue463();
        i.returnAnotherValue("foobar");
        return i.getValue();
    }
}