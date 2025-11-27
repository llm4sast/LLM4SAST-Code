import bugIdeas.*;
import java.util.Map;
public class Ideas_2009_08_27 {
    static public <K, V> int sumValueHashes(Map<K, V> m) {
        int sum = 0;
        for (K k : m.keySet())
            sum += m.get(k).hashCode();
        return sum;
    }
    static public <K, V> int getValueHash1(Map<K, V> m, K k) {
        if (m.containsKey(k))
            return m.get(k).hashCode();
        return 0;
    }
    static public <K, V> int getValueHash2(Map<K, V> m, K k) {
        if (m.get(k) != null)
            return m.get(k).hashCode();
        return 0;
    }
}