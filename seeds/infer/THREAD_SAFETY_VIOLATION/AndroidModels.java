import codetoanalyze.java.checkers.*;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.IInterface;
import android.util.DisplayMetrics;
import android.view.View;
import javax.annotation.concurrent.ThreadSafe;
interface AidlInterface extends IInterface {}
class MyActivity extends Activity {}
class MyResources extends Resources { public MyResources(AssetManager assets, DisplayMetrics metrics, Configuration config) { super(assets, metrics, config); } }
class MyView extends View {
  boolean mField;
  public MyView(Context c) { super(c); }
}
@ThreadSafe
class AndroidModels {
  Resources mResources;
  MyResources mMyResources;
  Object mField;
  public void resourceMethodFunctionalOk() { mField = mResources.getString(0); }
  public void customResourceMethodFunctionalOk() { mField = mResources.getString(0); }
  public void someResourceMethodsNotFunctionalBad() { mField = mResources.getConfiguration(); }
  public void findViewByIdOk1(MyView view) {
    MyView subview = (MyView) view.findViewById(-1);
    subview.mField = true; 
  }
  public void findViewByIdOk2(MyActivity activity) {
    MyView view = (MyView) activity.findViewById(-1);
    view.mField = true; 
  }
  public IBinder safeByDefaultInterfaceCallOk(AidlInterface i) { return i.asBinder(); }
}