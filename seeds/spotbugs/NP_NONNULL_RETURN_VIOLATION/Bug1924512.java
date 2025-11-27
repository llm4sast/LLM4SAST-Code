import sfBugs.*;
import edu.umd.cs.findbugs.annotations.NonNull;
public class Bug1924512 {
    @NonNull
    public static String nonNullLoop(String[] args) {
        String ret = null;
        for (String s : args) { ret = s; }
        return ret; 
    }
    @NonNull
    public static String nonNullIf(String[] args) {
        String ret = null;
        if (args.length > 0) { ret = args[0]; }
        return ret; 
    }
}