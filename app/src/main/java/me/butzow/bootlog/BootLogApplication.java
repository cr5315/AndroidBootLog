package me.butzow.bootlog;

import android.app.Application;

import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.butzow.bootlog.realm.Migration;


@DebugLog
public class BootLogApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
        builder.name(BuildConfig.REALM_NAME);
        builder.schemaVersion(BuildConfig.REALM_SCHEMA_VERSION);

        if (BuildConfig.DEBUG) {
            builder.deleteRealmIfMigrationNeeded();
        } else {
            builder.migration(new Migration());
        }

        Realm.setDefaultConfiguration(builder.build());
    }
}
