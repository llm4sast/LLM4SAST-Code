import ghIssues.*;
public class Issue2968 {
    public static void main(String[] args) {
        TestIf testIf = () -> { throw new ActualException(); };
        try {
            testIf.test();
        } catch (RedHerring1Exception | RedHerring2Exception exc)  {
        } catch (Exception exc) {
            exc.printStackTrace();
            if (exc instanceof CommonException) { System.out.println("this gets printed!"); }
        }
    }
    private interface TestIf { void test() throws ActualException, RedHerring1Exception, RedHerring2Exception; }
    private static class CommonException extends Exception { }
    private static class ActualException extends CommonException { }
    private static class RedHerring1Exception extends CommonException { }
    private static class RedHerring2Exception extends CommonException { }
}