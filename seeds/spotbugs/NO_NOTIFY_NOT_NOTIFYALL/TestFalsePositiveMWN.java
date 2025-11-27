public class TestFalsePositiveMWN {
    boolean[] arr = { false };
    public void set() throws Exception {
        synchronized (arr) {
            arr[0] = true;
            arr.notify();
        }
    }
    public void test() throws Exception {
        synchronized (arr) {
            while (!arr[0])
                arr.wait();
        }
    }
    public static void main(String[] args) throws Exception {
        TestFalsePositiveMWN test = new TestFalsePositiveMWN();
        test.set();
        test.test();
    }
}