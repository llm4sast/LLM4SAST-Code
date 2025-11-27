import com.google.errorprone.bugpatterns.*;
import java.io.Reader;
public class InvalidCharComparisonNegativeCases {
    public boolean testEquality(char c, Reader r) throws Exception {
        if (c == 0)
            return true;
        if (c == 0xffff)
            return true;
        int d;
        if ( (d = r.read()) == -1)
            return true;
        return false;
    }
}