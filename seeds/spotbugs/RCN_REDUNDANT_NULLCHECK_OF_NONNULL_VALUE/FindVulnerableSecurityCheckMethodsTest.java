import vulnerablesecuritycheckmethodstest.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.SecurityManager;
public class FindVulnerableSecurityCheckMethodsTest {
    Logger logger = Logger.getAnonymousLogger();
    public void badFindVulnerableSecurityCheckMethodsCheck() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { sm.checkRead("/temp/tempFile"); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void badFindVulnerableSecurityCheckMethodsCheck2() {
        try {
            System.getSecurityManager().checkRead("/temp/tempFile");
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    protected void badFindVulnerableSecurityCheckMethodsCheck3() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { sm.checkRead("/temp/tempFile"); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void badCallee() { (new FindVulnerableSecurityCheckMethodsTest()).badCalled(); }
    public void badCalled() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { sm.checkRead("/temp/tempFile"); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void badFindVulnerableSecurityCheckMethodsCheck4() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {  
                if(5 < 10) { sm.checkRead("/temp/tempFile"); }
            }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void badFindVulnerableSecurityCheckMethodsCheck5() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { sm.checkListen(10000); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void badFindVulnerableSecurityCheckMethodsCheck6(int x) {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { 
                if(x < 12) { sm.checkRead("/temp/tempFile"); }
            }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public final void goodVulnerableSecurityCheckMethodsTestCheck() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { sm.checkRead("/temp/tempFile"); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    private void goodVulnerableSecurityCheckMethodsTestCheck2() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { sm.checkRead("/temp/tempFile"); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodVulnerableSecurityCheckMethodsTestCheck4() {
        try {
            SecurityManager sm = System.getSecurityManager();
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodVulnerableSecurityCheckMethodsTestCheck5() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {  
                if(5 > 10) { sm.checkRead("/temp/tempFile"); }
            }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodVulnerableSecurityCheckMethodsTestCheck6() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {  
                if (5 > 10) { sm.getSecurityContext(); }
            }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodVulnerableSecurityCheckMethodsTestCheck7() {
        try {
            vulnerablesecuritycheckmethodstest.SecurityManager sm = new vulnerablesecuritycheckmethodstest.SecurityManager();
            if (sm != null) { sm.checkRead("/temp/tempFile"); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
}