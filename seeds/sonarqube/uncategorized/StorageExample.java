import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class StorageExample {
  public void sqliteExample(Activity activity) {
    SQLiteDatabase db =
        activity.openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null); // Sensitive
  }

  public void sharedPrefsExample(Activity activity) {
    SharedPreferences pref = activity.getPreferences(Context.MODE_PRIVATE); // Sensitive
  }

  public void realmExample() {
    RealmConfiguration config = new RealmConfiguration.Builder().build();
    Realm realm = Realm.getInstance(config); // Sensitive
  }
}
