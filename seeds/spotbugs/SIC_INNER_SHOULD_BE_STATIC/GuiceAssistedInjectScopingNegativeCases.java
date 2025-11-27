import com.google.errorprone.bugpatterns.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
public class GuiceAssistedInjectScopingNegativeCases {
  public class TestClass1 {
    public TestClass1(String unassisted1, String unassisted2) { }
  }
  @SuppressWarnings("foo")
  public class TestClass2 {
    public TestClass2(String unassisted, @Assisted String assisted) { }
  }
  @Singleton
  public class TestClass3 {
    public TestClass3(String unassisted1, String unassisted2) { }
  }
  public class TestClass4 {
    @Inject
    public TestClass4(@Assisted String assisted) { }
  }
  public class TestClass5 {
    @AssistedInject
    public TestClass5(String unassisted) { }
  }
  @Singleton
  public class TestClass6 {
    public TestClass6(@Assisted String assisted) { }
  }
  @Singleton
  public class TestClass7 {
    public TestClass7(String unassisted1, String unassisted2) { }
    public TestClass7(String unassisted, int i) { }
    public TestClass7(int i, String unassisted) { }
  }
  @Singleton
  public class TestClass8 {
    @Inject
    public TestClass8(String unassisted1, String unassisted2) { }
    @AssistedInject
    public TestClass8(String param, int i) { }
    @AssistedInject
    public TestClass8(int i, String param) { }
  }
  @Singleton
  public class TestClass9 {
    @Inject
    public TestClass9(String unassisted1, String unassisted2) { }
    @AssistedInject
    public TestClass9(String param, int i) { }
    @AssistedInject
    public TestClass9(int i, String param) { }
  }
  @Singleton
  public class TestClass10 {
    public TestClass10(@Assisted String assisted, String unassisted) { }
    public TestClass10(@Assisted String assisted, int i) { }
    public TestClass10(int i, @Assisted String assisted) { }
  }
}