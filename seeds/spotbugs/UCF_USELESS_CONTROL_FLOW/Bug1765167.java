import sfBugs.*;
public class Bug1765167 {
    public static void main(String[] args) {
        Bug1765167 b = new Bug1765167();
        b.method1();
        b.method2();
    }
    public void method1() {
        String newSource = "Wednesday 25th";
        String ignoreCaseNewSource = newSource.toLowerCase();
        int thTypeCharsIndex = -1;
        if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("1st")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("1th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("2nd")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("2th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("3rd")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("3th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("4th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("5th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("6th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("7th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("8th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("9th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("0th")) != -1)
            ;
        System.out.println("Trailing date characters index = " + thTypeCharsIndex);
        System.out.println("ending");
    }
    public void method2() {
        String newSource = "Wednesday 25th";
        String ignoreCaseNewSource = newSource.toLowerCase();
        int thTypeCharsIndex = -1;
        if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("1st")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("1th")) != -1)
            ;
        else if ((thTypeCharsIndex = ignoreCaseNewSource.indexOf("2nd")) != -1)
            ;
        System.out.println("Trailing date characters index = " + thTypeCharsIndex);
        System.out.println("ending");
    }
}