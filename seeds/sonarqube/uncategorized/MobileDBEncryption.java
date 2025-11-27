import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MobileDBEncryption {
    public void realm() {
        String key = "gb09ym9ydoolp3w886d0tciczj6ve9kszqd65u7d126040gwy86xqimjpuuc788g";
        RealmConfiguration config = new RealmConfiguration.Builder();
            .encryptionKey(key.toByteArray())
            .build();
        Realm realm = Realm.getInstance(config);
    }
}