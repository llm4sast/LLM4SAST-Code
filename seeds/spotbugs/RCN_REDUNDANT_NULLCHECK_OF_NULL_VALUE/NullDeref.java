import npe.*;
public class NullDeref {
    private int x;
    public static void main(String[] argv) {
        NullDeref n = new NullDeref();
        String s = "foo";
        n = null;
        if (null == n)
            System.out.println("This is silly");
        if (n == null) {
            System.out.println("Hey yo, it's null");
            System.out.println("Bad idea: " + n.x);
        } else {
            System.out.println("Safe to deref here: " + n.x);
            s = null;
        }
        int i = s.hashCode();
        s = null;
        if (argv.length > 1)
            System.out.println("argv.length > 1");
        else
            System.out.println("argv.length <= 1");
        int j = s.hashCode();
        s = argv[0];
        if (s == null)
            System.out.println("s is null");
        if (argv.length > 2)
            System.out.println("argv.length > 2");
        else
            System.out.println("argv.length <= 2");
        int k = s.hashCode();
    }
}