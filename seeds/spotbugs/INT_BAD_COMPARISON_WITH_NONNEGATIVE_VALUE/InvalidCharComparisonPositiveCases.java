import com.google.errorprone.bugpatterns.*;
import java.io.IOException;
import java.io.Reader;
public class InvalidCharComparisonPositiveCases {
    public boolean testEquality(char c, Reader r) throws IOException {
        if (c == -1)
            return true;
        char d;
        if ( (d = (char) r.read()) == -1)
            return true;
        return false;
    }
}