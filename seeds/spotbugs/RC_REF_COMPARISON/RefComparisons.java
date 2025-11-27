public class RefComparisons {
    public static boolean integerBadComparison(Integer a, Integer b) { return a == b; }
    public class MyClass1 { }
    public static boolean myClass1BadComparison(MyClass1 a, MyClass1 b) { return a == b; }
    public class MyClass2 { }
    public static boolean myClass2BadComparison(MyClass2 a, MyClass2 b) { return a == b; }
    class MyClass3 {
        private int x;
        public MyClass3(int x) { this.x = x; }
        public boolean equals(Object obj) {
            if (obj == this) { return true; }
            if (this == obj) { return true; }
            if (!(obj instanceof MyClass3)) { return false; }
            MyClass3 other = (MyClass3) obj;
            if(this == other) { return true; }
            return this.x == other.x;
        }
    }
    enum MyEnum { A, B, C; }
    public boolean enumComparison(MyEnum a, MyEnum b) { return a == b; }
}