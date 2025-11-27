import sfBugs.*;
public class Bug1864793 {
    public Bug1864793(String data_source_directory) {
        if (data_source_directory == null) { }
        this.data_source_directory = data_source_directory;
        String s = "hi !";
        if (s == null) { }
        System.out.println(this.data_source_directory);
    }
    public static void main(String[] args) { new Bug1864793("bar"); }
    private String data_source_directory;
}