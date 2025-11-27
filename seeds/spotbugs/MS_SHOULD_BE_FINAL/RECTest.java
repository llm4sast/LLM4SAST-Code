import java.io.IOException;
public class RECTest {
    public static Exception anException = new IOException();
    public static void staticThrowsException() throws Exception { throw new Exception(); }
    public static void staticThrowsIOException() throws IOException { throw new IOException(); }
    public void throwsNothing() { }
    public void throwsException() throws Exception { throw new Exception(); }
    public void throwsIOException() throws IOException { throw new IOException(); }
    public void throwsTwoExceptions() throws IOException, ClassNotFoundException { throw new IOException(); }
    private void dontTriggerEmptyExceptionHandler() { }
    public void testFail() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testFail2() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            throw new IOException();
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testFail3() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            IOException e = new IOException();
            throw e;
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testFail4() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            throwsIOException();
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testFail5() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            staticThrowsIOException();
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testFail6() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            throwsTwoExceptions();
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testPass() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            throw new Exception();
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testPass2() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            throw anException;
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testPass3() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            throwsException();
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testPass4() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            staticThrowsException();
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
    public void testPass5() {
        try {
            for (int i = 0; i < 1000; i++)
                for (int j = i; j < 1000; j++)
                    throwsNothing();
            throwsIOException();
        } catch (RuntimeException e) {
            dontTriggerEmptyExceptionHandler();
        } catch (Exception e) { dontTriggerEmptyExceptionHandler(); }
    }
}