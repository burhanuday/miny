package com.appallure.miny.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class PackageIntentListener extends BroadcastReceiver {

    public PackageIntentListener() { }

    @Override public void onReceive(Context context, Intent intent) {
        AppListUtil.refreshAppList(context);
    }

}