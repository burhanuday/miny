package com.appallure.miny.model;

import android.content.Intent;

public class App {
    String appName;
    Intent launchIntent;
    String packageName;

    public App(String appName, Intent launchIntent, String packageName){
        this.appName = appName;
        this.launchIntent = launchIntent;
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public Intent getLaunchIntent() {
        return launchIntent;
    }

    public String getPackageName() {
        return packageName;
    }
}
