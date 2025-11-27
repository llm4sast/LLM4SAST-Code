import sfBugs.*;
import java.util.Arrays;
public class Bug2120789 {
    private final Object[] data;
    public Bug2120789(Object[] a) {
        a = a.clone();
        this.data = a;
    }
    public Bug2120789(Object[] a, int length) {
        a = Arrays.copyOf(a, length);
        this.data = a;
    }
}