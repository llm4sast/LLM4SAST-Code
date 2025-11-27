import sfBugs.*;
public enum Bug1619879 {
    ONE, TWO;
    int f;
    public boolean equals(Bug1619879 foo) { return f == foo.f; }
}