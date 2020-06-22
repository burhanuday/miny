package com.appallure.miny.util;

import com.appallure.miny.model.App;

import java.util.Comparator;

public class SortAppsByName implements Comparator<App> {
    @Override
    public int compare(App o1, App o2) {
        return o1.getAppName().compareToIgnoreCase(o2.getAppName());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
