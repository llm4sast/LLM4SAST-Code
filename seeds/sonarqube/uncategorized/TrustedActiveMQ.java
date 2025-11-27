import org.apache.activemq.ActiveMQConnectionFactory;

public class TrustedActiveMQ {
  public void foo() {
    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
    factory.setTrustAllPackages(true); // Noncompliant
  }
}
