package me.butzow.bootlog.realm;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;


public class Boot extends RealmObject {
    @PrimaryKey private long primaryKey;
    private Date date;

    @SuppressWarnings("unused")
    public Boot() {}

    public Boot(Realm realm, Date date) {
        this.date = date;
        this.primaryKey = getNextPrimaryKey(realm);
    }

    private long getNextPrimaryKey(Realm realm) {
        RealmResults<Boot> boots = realm.where(Boot.class).findAllSorted("primaryKey");
        if (boots.isEmpty()) return 1;
        return boots.last().getPrimaryKey() + 1;
    }

    public long getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(long primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}