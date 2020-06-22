package com.appallure.miny.view;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.appallure.miny.R;
import com.appallure.miny.model.App;
import com.appallure.miny.util.AppListAdapter;
import com.appallure.miny.util.SortAppsByName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AllApps extends Fragment {
    private static final String TAG = "AllApps";
    EditText searchInput;

    private AppListAdapter appListAdapter;
    private String searchQuery;

    public AllApps() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        searchInput.setText("");
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.i("pageChanged", String.valueOf(isVisibleToUser));
        if (isVisibleToUser && isResumed()) {
            Log.i("pageChanged", "resume method called");
            searchInput.setText("");
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
            imm.showSoftInput(searchInput, InputMethodManager.SHOW_FORCED);
            searchInput.requestFocus();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_apps, container, false);

        searchInput = view.findViewById(R.id.et_search_app);

        List<App> appsList = getAppsList();
        Collections.sort(appsList, new SortAppsByName());

        RecyclerView appsRecyclerView = view.findViewById(R.id.rv_app_list);
        appsRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        appsRecyclerView.setLayoutManager(layoutManager);

        appListAdapter = new AppListAdapter(appsList, getContext());
        appsRecyclerView.setAdapter(appListAdapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = searchInput.getText().toString();
                Log.i("textChanged", query);
                if (query.length() == 0){
                    appListAdapter.getFilter().filter("");
                }else{
                    appListAdapter.getFilter().filter(query);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    List<App> getAppsList() {
        final PackageManager pm = getContext().getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<App> filteredPackages = new ArrayList<>();

        for (ApplicationInfo packageInfo : packages) {
            if(pm.getLaunchIntentForPackage(packageInfo.packageName) != null &&
                    !packageInfo.loadLabel(pm).toString().equalsIgnoreCase(getContext().getResources().getString(R.string.app_name))){
                filteredPackages.add(new App(packageInfo.loadLabel(pm).toString(),
                        pm.getLaunchIntentForPackage(packageInfo.packageName), packageInfo.packageName));

                Log.i("installedApps",packageInfo.loadLabel(pm).toString() + " " + packageInfo.toString());
            }
        }

        Log.i("list length", String.valueOf(filteredPackages.size()));

        return filteredPackages;
    }

}