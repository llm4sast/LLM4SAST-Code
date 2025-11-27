import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParserDOS {
  public void foo() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
  }
}
