import sfBugs.*;
public class Bug1941450 {
    void method() {
        String good = new String(new char[0]); 
        String bad = new String(new char[0]); 
        String good2 = new String(new char[0]); 
        String bad2 = new String(new char[0]); 
    }
}