class Bug1397 {
    private static String[] s1;
    public static Integer[] s2;
    private String[] f1;
    public Integer[] f2;
    public Bug1397(String[] arr) { f1 = arr; }
    public Bug1397(Integer[] arr) { f2 = arr; }
    public String[] getF1() { return f1; }
    public Integer[] getF2() { return f2; }
    public static void setS1(String[] arr) { s1 = arr; }
    public static void setS2(Integer[] arr) { s2 = arr; }
    public static String[] getS1() { return s1; }
    public static Integer[] getS2() { return s2; }
}