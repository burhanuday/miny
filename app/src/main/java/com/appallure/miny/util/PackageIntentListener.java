package com.appallure.miny.util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class PackageIntentListener extends BroadcastReceiver {

    public PackageIntentListener() { }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override public void onReceive(Context context, Intent intent) {
        Log.i("packagesChanged", "I am here");
        Toast.makeText(context, "Package stuff", Toast.LENGTH_LONG).show();
        AppListUtil.refreshAppList(context);
    }

}