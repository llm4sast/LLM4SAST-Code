import com.google.errorprone.bugpatterns.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.inject.servlet.RequestScoped;
public class GuiceAssistedInjectScopingPositiveCases {
  @Singleton
  public class TestClass {
    @Inject
    public TestClass(String unassisted, @Assisted String assisted) { }
  }
  @RequestScoped
  public class TestClass2 {
    @Inject
    public TestClass2(String unassisted, @Assisted String assisted) { }
  }
  @Singleton
  public class TestClass3 {
    @AssistedInject
    public TestClass3(String param) { }
  }
  @Singleton
  public class TestClass4 {
    @Inject
    public TestClass4(String unassisted, @Assisted String assisted) { }
    public TestClass4(String unassisted, int i) { }
    public TestClass4(int i, String unassisted) { }
  }
  @Singleton
  public class TestClass5 {
    public TestClass5(String unassisted1, String unassisted2) { }
    public TestClass5(String unassisted, int i) { }
    @AssistedInject
    public TestClass5(int i, String unassisted) { }
  }
  @javax.inject.Singleton
  public class TestClass6 {
    @javax.inject.Inject
    public TestClass6(String unassisted, @Assisted String assisted) { }
  }
}