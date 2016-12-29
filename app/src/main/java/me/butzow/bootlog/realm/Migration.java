package me.butzow.bootlog.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;


public class Migration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // No migrations yet
    }
}
