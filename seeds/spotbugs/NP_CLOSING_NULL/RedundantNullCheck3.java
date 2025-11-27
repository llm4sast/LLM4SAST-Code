import java.io.FileInputStream;
import edu.umd.cs.findbugs.annotations.Confidence;
import edu.umd.cs.findbugs.annotations.NoWarning;
class RedundantNullCheck3 {
    public void test1() throws Exception {
        FileInputStream in = new FileInputStream("/dev/null");
        if (in == null)
            in.close();
    }
    @NoWarning(value="RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE", confidence = Confidence.MEDIUM)
    public void test2() throws Exception {
        FileInputStream in = new FileInputStream("/dev/null");
        if (in != null)
            in.close();
    }
    public void test3() throws Exception {
        FileInputStream in = new FileInputStream("/dev/null");
        if (in == null)
            throw new IllegalStateException("in is null");
    }
    public void test4() throws Exception {
        FileInputStream in = new FileInputStream("/dev/null");
        if (in != null)
            throw new IllegalStateException("in is null");
    }
    public void test5() throws Exception {
        FileInputStream in = null;
        if (in == null)
            in.close();
    }
    public void test6() throws Exception {
        FileInputStream in = null;
        if (in != null)
            in.close();
    }
    public void test7() throws Exception {
        FileInputStream in = null;
        if (in == null)
            throw new IllegalStateException("in is null");
    }
    public void test8() throws Exception {
        FileInputStream in = null;
        if (in != null)
            throw new IllegalStateException("in is null");
    }
    public void test9(boolean b) throws Exception {
        FileInputStream in = null;
        if (b)
            in = new FileInputStream("/dev/null");
        if (in == null)
            in.close();
    }
    public void test10(boolean b) throws Exception {
        FileInputStream in = null;
        if (b)
            in = new FileInputStream("/dev/null");
        if (in != null)
            in.close();
    }
    public void test11(boolean b) throws Exception {
        FileInputStream in = null;
        if (b)
            in = new FileInputStream("/dev/null");
        if (in == null)
            throw new IllegalStateException("in is null");
    }
    public void test12(boolean b) throws Exception {
        FileInputStream in = null;
        if (b)
            in = new FileInputStream("/dev/null");
        if (in != null)
            throw new IllegalStateException("in is null");
    }
}