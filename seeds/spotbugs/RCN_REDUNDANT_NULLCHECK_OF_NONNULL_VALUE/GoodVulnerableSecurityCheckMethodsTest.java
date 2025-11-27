import vulnerablesecuritycheckmethodstest.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.SecurityManager;
public final class GoodVulnerableSecurityCheckMethodsTest {
    Logger logger = Logger.getAnonymousLogger();
    public void goodVulnerableSecurityCheckMethodsTestCheck() {
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
    protected void goodFindVulnerableSecurityCheckMethodsCheck3() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { sm.checkRead("/temp/tempFile"); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodFindVulnerableSecurityCheckMethodsCheck5() {
        try {
            System.getSecurityManager().checkRead("/temp/tempFile");
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void badCallee() { (new FindVulnerableSecurityCheckMethodsTest()).badCalled(); }
    public void badCalled() {
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
    public void goodFindVulnerableSecurityCheckMethodsCheck6() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {  
                if (5 < 10) { sm.checkRead("/temp/tempFile"); }
            }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodFindVulnerableSecurityCheckMethodsCheck7() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { sm.checkListen(10000); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodVulnerableSecurityCheckMethodsTestCheck5() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {  
                if (5 > 10) { sm.checkRead("/temp/tempFile"); }
            }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodVulnerableSecurityCheckMethodsTestCheck6(int x) {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) { 
                if (x < 12) { sm.checkRead("/temp/tempFile"); }
            }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodVulnerableSecurityCheckMethodsTestCheck7() {
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {  
                if (5 > 10) { sm.getSecurityContext(); }
            }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
    public void goodVulnerableSecurityCheckMethodsTestCheck8() {
        try {
            vulnerablesecuritycheckmethodstest.SecurityManager sm = new vulnerablesecuritycheckmethodstest.SecurityManager();
            if (sm != null) { sm.checkRead("/temp/tempFile"); }
        } catch (SecurityException se) { logger.log(Level.WARNING, se.toString()); }
    }
}