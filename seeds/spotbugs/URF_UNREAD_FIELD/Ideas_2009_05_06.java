import bugIdeas.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
public class Ideas_2009_05_06 {
    @ExpectWarning("LG_LOST_LOGGER_DUE_TO_WEAK_REFERENCE")
    public static void initLogging() throws SecurityException, IOException {
        Logger logger = Logger.getLogger("edu.umd.cs.findbugs");
        logger.addHandler(new FileHandler()); 
        logger.setUseParentHandlers(false); 
        logger.setLevel(Level.FINER);
        logger.setFilter(new Filter() {
            @Override
            public boolean isLoggable(LogRecord arg0) { return true; }
        });
    }
    static Object foo1;
    static List<Logger> foo2 = new ArrayList<Logger>();
    public static void falsePositive1() {
        Logger logger = Logger.getLogger("edu.umd.cs.findbugs");
        logger.setLevel(Level.FINER);
        foo1 = logger;
    }
    public static void falsePositive2() {
        Logger logger = Logger.getLogger("edu.umd.cs.findbugs");
        logger.setLevel(Level.FINER);
        foo2.add(logger);
    }
    public static void main(String[] args) throws Exception {
        initLogging(); 
        System.gc(); 
        Logger.getLogger("com.google.gse").info("Some message"); 
    }
}