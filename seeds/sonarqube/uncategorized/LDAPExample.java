import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LDAPExample {
  public void foo() throws Exception {
    // Set up the environment for creating the initial context
    Hashtable<String, Object> env = new Hashtable<String, Object>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=JNDITutorial");
    // Use anonymous authentication
    env.put(Context.SECURITY_AUTHENTICATION, "none"); // Noncompliant
    // Create the initial context
    DirContext ctx = new InitialDirContext(env);
  }
}
