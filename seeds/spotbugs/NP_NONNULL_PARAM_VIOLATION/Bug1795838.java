import sfBugs.*;
public class Bug1795838 {
    public static void main(String arg[]) {
        Bug1795838 fb = new Bug1795838();
        fb.foo();
    }
    public void foo() {
        int x;
        x = bar(); 
        System.out.println("X is " + x);
        Integer tmp;
        tmp = bar();
        int i = tmp.intValue(); 
        System.out.println("I is " + i);
        i = Integer.parseInt(null); 
        System.out.println("I is now" + i);
    }
    public Integer bar() { return null; }
}