package com.appallure.miny.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.appallure.miny.R;
import com.appallure.miny.model.App;

import java.util.ArrayList;
import java.util.List;

public class AppListUtil {

    private static List<App> filteredPackages = new ArrayList<>();;

    public static void clearAppsList(){
        filteredPackages.clear();
    }

    public static void refreshAppList(Context context) {
        final PackageManager pm = context.getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        filteredPackages.clear();
        for (ApplicationInfo packageInfo : packages) {
            if(pm.getLaunchIntentForPackage(packageInfo.packageName) != null &&
                    !packageInfo.loadLabel(pm).toString().equalsIgnoreCase(context.getResources().getString(R.string.app_name))){
                filteredPackages.add(new App(packageInfo.loadLabel(pm).toString(), packageInfo.packageName));

                Log.i("installedApps",packageInfo.loadLabel(pm).toString() + " " + packageInfo.toString());
            }
        }
    }

    public static List<App> getAppsList(Context context) {
        if(filteredPackages.size() != 0){
            return  filteredPackages;
        }
        final PackageManager pm = context.getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            if(pm.getLaunchIntentForPackage(packageInfo.packageName) != null &&
                    !packageInfo.loadLabel(pm).toString().equalsIgnoreCase(context.getResources().getString(R.string.app_name))){
                filteredPackages.add(new App(packageInfo.loadLabel(pm).toString(), packageInfo.packageName));

                Log.i("installedApps",packageInfo.loadLabel(pm).toString() + " " + packageInfo.toString());
            }
        }

        Log.i("list length", String.valueOf(filteredPackages.size()));

        return filteredPackages;
    }
}
