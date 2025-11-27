public class Naming {
    public static class NamingException { }
    public static class NamingBaseException extends Exception { private static final long serialVersionUID = 1L; }
    public static class NamingBaseChildException extends NamingBaseException { private static final long serialVersionUID = 2L; }
    public static class TrickyName extends NamingBaseChildException { private static final long serialVersionUID = 3L; }
    public static class FinalException extends TrickyName { private static final long serialVersionUID = 4L; }
    public static void main(String args[]) throws FinalException {
        boolean blah = true;
        if (blah) { throw new FinalException(); }
    }
}