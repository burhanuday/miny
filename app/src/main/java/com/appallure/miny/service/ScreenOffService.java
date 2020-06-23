package com.appallure.miny.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.RequiresApi;

public class ScreenOffService extends AccessibilityService {

    public static ScreenOffService instance;;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        Log.i("accser", "connected");
        instance = this;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void turnScreenOff() {
        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
    }
}
