import sfBugs.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
public class Bug1830576 {
    public Set<String> keySet() { return null; }
    public String get(String key) { return null; }
    public static void main(String[] args) {
        method0();
        method1();
        method2();
    }
    public static void method0() {
        Bug1830576 fakeMap = new Bug1830576();
        Map<String, String> realMap = new HashMap<String, String>();
        for (String key : fakeMap.keySet()) {
            String value = fakeMap.get(key);
            System.out.println(value + realMap.get(key));
        }
    }
    public static void method1() {
        Map<String, String> realMap = new HashMap<String, String>();
        for (String key : realMap.keySet()) {
            String value = realMap.get(key);
            System.out.println(value + realMap.get(key));
        }
    }
    public static void method2() {
        Map<String, String> realMap2 = new Bug1830576_helper<String, String>();
        for (String key : realMap2.keySet()) {
            String value = realMap2.get(key);
            System.out.println(value + realMap2.get(key));
        }
    }
    private static class Bug1830576_helper<K, V> implements Map<K, V> {
        @Override
        public void clear() { }
        @Override
        public boolean containsKey(Object key) { return false; }
        @Override
        public boolean containsValue(Object value) { return false; }
        @Override
        public Set<java.util.Map.Entry<K, V>> entrySet() { return null; }
        @Override
        public V get(Object key) { return null; }
        @Override
        public boolean isEmpty() { return false; }
        @Override
        public Set<K> keySet() { return null; }
        @Override
        public V put(K key, V value) { return null; }
        @Override
        public void putAll(Map<? extends K, ? extends V> t) { }
        @Override
        public V remove(Object key) { return null; }
        @Override
        public int size() { return 0; }
        @Override
        public Collection<V> values() { return null; }
    }
}