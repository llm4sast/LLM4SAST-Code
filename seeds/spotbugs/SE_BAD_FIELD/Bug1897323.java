import sfBugs.*;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
public class Bug1897323 implements javax.ejb.SessionBean {
    Thread t = new Thread() {
        @Override
        public void run() { System.out.println("Hello"); }
    };
    @Override
    public void ejbActivate() throws EJBException, RemoteException { }
    @Override
    public void ejbPassivate() throws EJBException, RemoteException { }
    @Override
    public void ejbRemove() throws EJBException, RemoteException { }
    @Override
    public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException { }
    public Thread getThread() { return t; }
}