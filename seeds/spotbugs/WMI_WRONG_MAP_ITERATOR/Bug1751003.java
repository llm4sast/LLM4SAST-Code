import sfBugs.*;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
public class Bug1751003 {
    public static void main(String args[]) {
        Bug1751003 b = new Bug1751003();
        b.sortedMapIterate();
        b.useKeyAndValIterate();
    }
    public void sortedMapIterate() {
        SortedMap<Integer, Integer> sm = new TreeMap<Integer, Integer>();
        sm.put(1, 2);
        sm.put(3, 4);
        sm.put(5, 6);
        for (Integer I : sm.keySet()) {
            Integer J = sm.get(I);
            if (J > 10) { System.out.println(""); }
        }
    }
    public void useKeyAndValIterate() {
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
        hm.put(1, 2);
        hm.put(3, 4);
        hm.put(5, 6);
        for (Integer I : hm.keySet()) {
            Integer J = hm.get(I);
            if (J > 10 && I > 20) { System.out.println(""); }
        }
    }
}