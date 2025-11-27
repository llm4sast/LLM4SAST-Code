import sfBugs.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.annotations.NoWarning;
public class Bug1923609 {
    @NoWarning("DMI")
    public void writeMapStringString() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new ByteArrayOutputStream());
        Map<String, String> m = new HashMap<String, String>();
        m.put("red", "blue");
        os.writeObject(m);
    }
    static class DummyThread extends Thread {
        @Override
        public void run() { System.out.println("Dummy"); }
    }
    @ExpectWarning("DMI")
    public void writeMapThreadThread() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new ByteArrayOutputStream());
        Map<Thread, Thread> m = new HashMap<Thread, Thread>();
        m.put(new DummyThread(), new DummyThread());
        os.writeObject(m);
    }
    static class MyMap<T1, T2> implements Map<T1, T2> {
        private Thread dummy; 
        public void setThread(Thread t) { dummy = t; }
        public Thread getThread() { return dummy; }
        @Override
        public void clear() { }
        @Override
        public boolean containsKey(Object key) { return false; }
        @Override
        public boolean containsValue(Object value) { return false; }
        @Override
        public Set<java.util.Map.Entry<T1, T2>> entrySet() { return null; }
        @Override
        public T2 get(Object key) { return null; }
        @Override
        public boolean isEmpty() { return false; }
        @Override
        public Set<T1> keySet() { return null; }
        @Override
        public T2 put(T1 key, T2 value) { return null; }
        @Override
        public void putAll(Map<? extends T1, ? extends T2> t) { }
        @Override
        public T2 remove(Object key) { return null; }
        @Override
        public int size() { return 0; }
        @Override
        public Collection<T2> values() { return null; }
    }
    @ExpectWarning("DMI")
    public void writeMyMap() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new ByteArrayOutputStream());
        Map<String, String> m = new MyMap<String, String>();
        m.put("red", "blue");
        os.writeObject(m);
    }
}