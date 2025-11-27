import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class SwitchDup {
    public int simpleIf(boolean b) {
        int n = 5;
        if (b)
            n = n * n + 3;
        else
            n = n * n + 3;
        return n;
    }
    public int nestedIf(boolean b) {
        int n = 5;
        if (b) {
            if (System.getProperty("foo") != null)
                n += 2;
            else
                n += 3;
        } else {
            if (System.getProperty("foo") != null)
                n += 2;
            else
                n += 3;
        }
        return n;
    }
    public int simpleCase(int which) {
        int n = 5;
        switch (which) {
        case 0:
            n = n * n;
            break;
        case 1:
            if (System.getProperty("foo") != null)
                n += 2;
            else
                n += 3;
            n = n * n;
            break;
        case 2:
            n = n * n;
            break;
        default:
            n = n * n;
        }
        return n;
    }
    public int casesFallThrough(int which) {
        int n = 5;
        switch (which) {
        case 0:
            n = n * n;
        case 1:
            n = n * n;
        case 2:
            n = n * n;
        default:
            n = n * n;
        }
        return n;
    }
    public int returnCase(int which) {
        int n = 5;
        switch (which) {
        case 0:
            return n * n;
        case 1:
            return n * n;
        case 2:
            return n * n;
        default:
            return n * n;
        }
    }
    @ExpectWarning("SF_SWITCH_NO_DEFAULT")
    public int noDefault(int which) {
        int n = 5;
        switch (which) {
        case 0:
            n = n * n;
            break;
        case 1:
            n = n * n;
            break;
        case 2:
            n = n * n;
            break;
        }
        n = n * n;
        return n;
    }
    public int okCase(int which) {
        int n = 5;
        switch (which) {
        case 0:
            n = 10;
            break;
        case 1:
            n = 11;
            break;
        case 2:
            n = 12;
            break;
        }
        return n;
    }
    public int crazy(int n) {
        switch (n) {
        case 1:
            n = n * n;
            break;
        case 2:
            n += 1;
            break;
        case 3:
            throw new IllegalArgumentException("not implemented");
        case 4:
            throw new IllegalArgumentException("not implemented");
        }
        n = n * n;
        return n;
    }
    public String peeterswim(int index) {
        switch (index) {
        case 0:
            return "0";
        case 1:
            return "1";
        case 2:
            return "2";
        default:
            return null;
        }
    }
    public int g_korland() {
        int a = 1;
        switch (a) {
        case 1:
            return 1;
        case 2:
            return 2;
        }
        throw new IllegalArgumentException();
    }
    enum Code { A, B }
    public void ruimo(Code code) {
        switch (code) {
        case A:
            System.out.println("Hello");
            break;
        case B:
            System.out.println("Hello");
            break;
        default:
            break;
        }
    }
}