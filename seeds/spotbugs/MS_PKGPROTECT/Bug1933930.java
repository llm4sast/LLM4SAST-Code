import sfBugs.*;
public class Bug1933930 {
    public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
    public static final char[] US_ENGLISH_MAPPING = US_ENGLISH_MAPPING_STRING.toCharArray();
    public static void main(String[] args) { System.out.println(US_ENGLISH_MAPPING); }
}