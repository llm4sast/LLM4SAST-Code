import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;

public class XXEAttack {
  public void decode1() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Noncompliant
    System.out.println(factory);
  }

  public void decode2() {
    XMLInputFactory factory = XMLInputFactory.newInstance(); // Noncompliant
    System.out.println(factory);
  }
}
