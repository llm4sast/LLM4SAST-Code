import sfBugs.*;
public class Bug1759836 {
    public static void main(String args[]) {
        ProcessBuilder pb = new ProcessBuilder();
        pb.redirectErrorStream();
    }
}