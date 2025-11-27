import ghIssues.issue600.*;
public class ExplicitNullCheckSimple {
    public void test() throws Exception {
        MyAutocloseable closeable = MyAutocloseable.create();
        try {
            closeable.exampleMethod();
        } catch (Throwable var5) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Throwable var4) { var5.addSuppressed(var4); }
            }
            throw var5;
        }
        if (closeable != null) { closeable.close(); }
    }
}