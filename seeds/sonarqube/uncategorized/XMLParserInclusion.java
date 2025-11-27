import javax.xml.parsers.SAXParserFactory;

public class XMLParserInclusion {

  public void foo() throws Exception {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setXIncludeAware(true); // Noncompliant
    factory.setFeature("http://apache.org/xml/features/xinclude", true); // Noncompliant
  }
}
