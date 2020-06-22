package com.appallure.miny.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.appallure.miny.view.AllApps;
import com.appallure.miny.view.Home;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Home(); //ChildFragment1 at position 0
            case 1:
                return new AllApps(); //ChildFragment2 at position 1
        }
        return new Home(); //does not happen
    }

    @Override
    public int getCount() {
        return 2; //two fragments
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}