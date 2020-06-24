package com.appallure.miny.model;

import android.content.Intent;

public class App {
    String appName;
    String packageName;

    public App(String appName, String packageName){
        this.appName = appName;
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }
}
