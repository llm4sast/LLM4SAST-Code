import com.google.errorprone.bugpatterns.*;
public class InvalidSignedByteNegativeCases {
  public boolean testEquality(byte [] b, byte x) {
     if (x == 1)
         return true;
     if (x == -2)
         return true;
     if (x == 127)
         return true;
     if (x != 1)
         return true;
     if (b[0] == 1)
         return true;
     if (b[0] == -2)
         return true;
     if (b[0] == -127)
         return true;
    return false;
  }
}