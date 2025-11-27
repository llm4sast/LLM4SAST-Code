import npe.*;
public class NullDeref2 {
    public void detectedNullPointerInExceptionPath() {
        Object thisIsNull = null;
        if (thisIsNull == null) {
            try {
                System.out.println("hello");
                thisIsNull = "notnull";
            } catch (RuntimeException ex) { System.out.println(thisIsNull.getClass()); }
        }
    }
    public void possibleNullPointerInExceptionPath() {
        Object thisIsNull = null;
        if (thisIsNull == null) {
            try {
                System.out.println("hello");
                thisIsNull = "notnull";
            } catch (RuntimeException ex) { }
            System.out.println(thisIsNull.getClass());
        }
    }
    public void possibleNullPointerInNormalPath() {
        Object thisIsNull = null;
        if (thisIsNull == null) {
            try {
                System.out.println("hello");
            } catch (RuntimeException ex) { thisIsNull = "notnull"; }
            System.out.println(thisIsNull.getClass());
        }
    }
}