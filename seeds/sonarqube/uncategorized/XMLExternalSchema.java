import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.XMLConstants;
import org.dom4j.io.SAXReader;
import org.jdom2.input.SAXBuilder;

public class XMLExternalSchema {
    public void foo() {
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        factory1.setValidating(true); // Noncompliant
        factory1.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true); // Noncompliant

        SAXParserFactory factory2 = SAXParserFactory.newInstance();
        factory2.setValidating(true); // Noncompliant
        factory2.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true); // Noncompliant

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true); // Noncompliant

        SAXReader xmlReader = new SAXReader(); // Noncompliant
        xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true);  // Noncompliant
        For Jdom2 library:

        SAXBuilder builder = new SAXBuilder();
        builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true); // Noncompliant
    }
}