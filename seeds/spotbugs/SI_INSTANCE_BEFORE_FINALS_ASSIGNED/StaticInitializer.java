public class StaticInitializer {
    static StaticInitializer superStaticInitializer = new StaticInitializer();
    static final long startTime = System.currentTimeMillis();
    public StaticInitializer() { System.out.println(System.currentTimeMillis() - startTime + " milliseconds have elapsed since start of program"); }
    public static void main(String[] args) {
        int i = 0;
        System.out.println(i);
    }
}