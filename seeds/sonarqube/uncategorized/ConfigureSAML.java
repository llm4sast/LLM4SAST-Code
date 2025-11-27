import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.StaticBasicParserPool;

public class ConfigureSAML {
  public ParserPool parserPool1() {
    StaticBasicParserPool staticBasicParserPool = new StaticBasicParserPool();
    staticBasicParserPool.setIgnoreComments(false); // Noncompliant
    return staticBasicParserPool;
  }

  public ParserPool parserPool2() {
    BasicParserPool basicParserPool = new BasicParserPool();
    basicParserPool.setIgnoreComments(false); // Noncompliant
    return basicParserPool;
  }
}
