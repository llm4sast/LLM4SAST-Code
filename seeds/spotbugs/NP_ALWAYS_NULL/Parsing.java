import tigerTraps.*;
public class Parsing {
    public static Integer parseInt(String s) { return (s == null) ? (Integer) null : Integer.parseInt(s); }
    public static void main(String[] args) { System.out.println(parseInt("-1") + " " + parseInt(null) + " " + parseInt("1")); }
}