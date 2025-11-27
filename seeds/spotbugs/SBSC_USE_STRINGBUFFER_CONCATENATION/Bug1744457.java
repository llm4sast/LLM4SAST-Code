import sfBugs.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class Bug1744457 {
    public static void main(String args[]) {
        Bug1744457 b = new Bug1744457();
        b.justFirst();
        b.justSecond();
        b.both();
    }
    @ExpectWarning("ES_COMPARING_STRINGS_WITH_EQ,SBSC_USE_STRINGBUFFER_CONCATENATION")
    public void both() {
        List<String> A = new ArrayList<String>();
        A.add("Alex");
        A.add("john");
        A.add("lily");
        A.add("tracy");
        Iterator<String> it = A.iterator();
        while (it.hasNext()) {
            String retrieve = it.next();
            if (retrieve != null && !retrieve.equals(""))
                System.out.println(retrieve);
        }
        it = A.iterator();
        String add = "";
        while (it.hasNext()) {
            String retrieve = it.next();
            if (retrieve != null && retrieve != "")
               add += retrieve;
            System.out.println(add);
        }
    }
    public void justFirst() {
        List<String> A = new ArrayList<String>();
        A.add("Alex");
        A.add("john");
        A.add("lily");
        A.add("tracy");
        Iterator<String> it = A.iterator();
        while (it.hasNext()) {
            String retrieve = it.next();
            if (retrieve != null && !retrieve.equals(""))
                System.out.println(retrieve);
        }
    }
    @ExpectWarning("ES_COMPARING_STRINGS_WITH_EQ,SBSC_USE_STRINGBUFFER_CONCATENATION")
    public void justSecond() {
        List<String> A = new ArrayList<String>();
        A.add("Alex");
        A.add("john");
        A.add("lily");
        A.add("tracy");
        Iterator <String>it = A.iterator();
        it = A.iterator();
        String add = "";
        while (it.hasNext()) {
            String retrieve = it.next();
            if (retrieve != null && retrieve != "")
                add += retrieve;
            System.out.println(add);
        }
    }
}