public class DM_BOXED_PRIMITIVE_TOSTRING_bug0 {
    public String testBad1(int value) {
        return new Integer(value).toString();
    }
}
