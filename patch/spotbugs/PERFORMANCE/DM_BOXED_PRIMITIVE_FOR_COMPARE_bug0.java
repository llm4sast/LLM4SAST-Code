public class DM_BOXED_PRIMITIVE_FOR_COMPARE_bug0 {
    public int compareTo(int a, int b) {
        return ((Integer)a).compareTo(b);
    }
}
