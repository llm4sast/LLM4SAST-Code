import sfBugs.*;
import java.io.IOException;
public class Bug1964520 extends Exception {
    private static final long serialVersionUID = 1L;
    public void test(Exception e) {
        if (((Bug1964520) e).toString().equals("")) { System.out.println("toString is blank"); }
        if (e instanceof IOException && ((Bug1964520) e).toString().equals("")) { System.out.println("toString is blank"); }
        if (e instanceof Bug1964520 && ((Bug1964520) e).toString().equals("")) { System.out.println("toString is blank"); }
        if (e instanceof Bug1964520) {
            if (((Bug1964520) e).toString().equals("")) { System.out.println("toString is blank"); }
        }
    }
}