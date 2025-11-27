import sfBugs.*;
public class Bug1908621 {
    public static Boolean get_bool() {
        Boolean test;
        if (1 == (int) Math.round(Math.random())) {
            test = null;
        } else { test = true; }
        return test;
    }
    public static void main(String[] args) {
        Boolean test = get_bool();
        if (test) { System.out.println("TRUE"); }
        Boolean test2;
        if (1 == (int) Math.round(Math.random())) {
            test2 = null;
        } else { test2 = true; }
        if (test2) { System.out.println("TRUE"); }
    }
}