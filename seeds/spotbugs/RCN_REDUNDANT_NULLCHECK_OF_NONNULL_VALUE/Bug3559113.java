import sfBugs.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
public class Bug3559113 {
 private static final Logger magnifySearchLogger = Logger.getLogger("ibi.search.search");
 private final ArrayList<String> fieldnames=new ArrayList<String>();
 private final ArrayList<String> fieldvalues=new ArrayList<String>();
 @SuppressWarnings("unchecked")
 public ArrayList<String>getFields() { return (ArrayList<String>) fieldnames.clone(); }
 @SuppressWarnings("unchecked")
 public ArrayList<String>getValues() { return (ArrayList<String>) fieldvalues.clone(); }
 public Bug3559113(final HttpServletRequest req) {
     String reqfieldParameter=req.getParameter("requiredfields");
     if (reqfieldParameter != null) {
         String reqfields=reqfieldParameter;
         String [] ss_and=null;
         String [] ss_or=null;
         if (reqfields != null) {
             if (reqfields.indexOf('|') > -1 )
                 ss_or=reqfields.split("\\|");
             else
                 ss_and=reqfields.split("\\.");
             String [] ss_l=ss_or;
             for (int Logical_operator=1;Logical_operator <3;Logical_operator++) {
                 if (ss_l == null) {
                     ss_l=ss_and;
                     continue;
                 }
                 final int len=ss_l.length;
                 for (int l_index=0;l_index<len;l_index++) {
                     final String [] ss=decode(ss_l[l_index]).split(":");
                     if (ss.length > 1) {
                         addField(ss[0], 
                                  ss[1]); 
                     }
                 }
                 ss_l=ss_and;
             }
         }
     }
     String newRequiredFields=req.getParameter("newrequiredfields");
     if (newRequiredFields != null) {
         String  [] newrequiredfields=decode(req.getParameter("newrequiredfields")).split(":");
         if (newrequiredfields != null && newrequiredfields.length > 1
                 ) {
             addField(newrequiredfields[0], 
                      newrequiredfields[1]); 
         }
     }
 }
 private void addField(
         String fieldname,
         String fieldvalue) {
     String decodedfieldname=fieldname;
     String decodedfieldvalue=fieldvalue;
     if (magnifySearchLogger.isLoggable(Level.FINER))
         magnifySearchLogger.finer("RequiredFields():adecodedfieldnameddField: decodedfieldname="+decodedfieldname+", decodedfieldvalue= "+decodedfieldvalue);
     fieldnames.add(fieldname);
     fieldvalues.add(fieldvalue);
 }
 private String decode (final String s) {
     if (s == null)
         return null;
     try {
       return java.net.URLDecoder.decode(s, "UTF8");
     } catch ( UnsupportedEncodingException ex ) {
       if( magnifySearchLogger.isLoggable(Level.FINER) )
         magnifySearchLogger.finer("decode(): Use ibi.util.URLDecoder.decode()");
       return  s;
     }
 }
}