package com.appallure.miny.inter;

public interface AppListOnClickListener {
    void onClick(String packageName, String appName);
    void onLongClick(String packageName, String appName);
}
