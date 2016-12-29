package me.butzow.bootlog.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import hugo.weaving.DebugLog;
import io.realm.Realm;
import me.butzow.bootlog.realm.Boot;

@DebugLog
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> r.copyToRealmOrUpdate(new Boot(r, new Date())));
            realm.close();
        }
    }
}
