import sfBugs.*;
import edu.umd.cs.findbugs.annotations.NonNull;
public class Bug3502202 {
    static class Foo {
        @NonNull
        private String bar; 
        public int getBarLength() { return bar.length(); }
        public void setBar(String bar) { this.bar = bar; }
    }
    static class Baz {
        public int getBarLength() { return new Foo().getBarLength(); }
        public static void main(String[] arg) { System.out.println("bar length =" + new Baz().getBarLength()); }
    }
}