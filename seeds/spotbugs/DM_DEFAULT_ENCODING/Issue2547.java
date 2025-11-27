import ghIssues.issue2547.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Issue2547 {
    public void throwingExCtor() throws MyEx { throw new MyEx(""); }
    public void notThrowingExCtor() { new MyEx(""); }
    public void createAndThrowExCtor() throws MyEx {
        MyEx e = new MyEx("");
        throw e;
    }
    public void notThrowingExCtorCaller() { MyEx.somethingWentWrong(""); }
    public void createAndThrowExCtorCaller() throws MyEx {
        MyEx e = MyEx.somethingWentWrong("");
        throw e;
    }
    public void notThrowingExCtorCallerOutside() { ExceptionFactory.createMyException(5); }
    public void createAndThrowExCtorCallerOutside() throws MyEx {
        MyEx e = ExceptionFactory.createMyException(5);
        throw e;
    }
    public void createAndThrowExCtorCallerOutside23(File f) throws IOException {
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            fw.write("string");
        } catch (IOException e) {
            IOException ioe = new IOException("IOException caught");
            ioe.initCause(e);
            throw ioe;
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) { System.err.println("Exception caught, no error"); }
            }
        }
    }
}