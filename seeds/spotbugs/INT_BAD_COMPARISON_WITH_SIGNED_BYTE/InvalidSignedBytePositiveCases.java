import com.google.errorprone.bugpatterns.*;
public class InvalidSignedBytePositiveCases {
    public boolean testEquality(byte[] b, byte x) {
        if (x == 255)
            return true;
        if (x == -255)
            return true;
        if (x == 128)
            return true;
        if (x != 255)
            return true;
        if (b[0] == 255)
            return true;
        if (b[0] == 128)
            return true;
        if (b[0] == -255)
            return true;
        return false;
    }
}