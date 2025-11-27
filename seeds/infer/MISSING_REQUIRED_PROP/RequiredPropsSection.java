import codetoanalyze.java.litho.*;
import com.facebook.litho.sections.Section;
class RequiredPropsSection {
  public MySection mMySection;
  public Section buildWithAllOk() { return mMySection.create().prop1(new Object()).prop2(new Object()).build(); }
  public Section buildWithout2Ok() { return mMySection.create().prop1(new Object()).build(); }
  public Section buildWithout1Bad() { return mMySection.create().prop2(new Object()).build(); }
}