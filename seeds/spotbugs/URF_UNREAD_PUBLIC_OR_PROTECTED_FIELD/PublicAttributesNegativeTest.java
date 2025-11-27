import java.util.HashMap;
import java.util.Map;
class PublicAttributesNegativeTest {
    public int attr1 = 0;
    public static final Map<Integer, String> HM = new HashMap<Integer, String>();
    public static final String[] ITEMS = { "foo", "bar", "baz" };
    PublicAttributesNegativeTest(int a1) { attr1 = a1; }
}