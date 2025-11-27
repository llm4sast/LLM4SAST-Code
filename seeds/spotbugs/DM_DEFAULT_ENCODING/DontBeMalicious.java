import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
public class DontBeMalicious {
    final static HashMap myMap = new HashMap();
    public static void main(String[] args) { }
    public void lazyMethod() {
        try {
            BufferedReader x = new BufferedReader(new FileReader("HelloHello"));
        } catch (FileNotFoundException e) { }
    }
}