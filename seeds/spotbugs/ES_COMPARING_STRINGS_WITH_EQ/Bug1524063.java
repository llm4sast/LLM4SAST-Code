import sfBugs.*;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug1524063 {
    @NoWarning("SBSC")
    void fizzBuzz() {
        String x;
        for (int i = 0; i < 100; i++) {
            x = "";
            if (i % 3 == 0)
                x = "Fizz";
            if (i % 5 == 0)
                x += "Buzz";
            if (x == "")
                x += i;
            System.out.println(i);
        }
    }
}