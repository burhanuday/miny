package com.appallure.miny.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
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

import com.appallure.miny.MainActivity;
import com.appallure.miny.R;
import com.appallure.miny.inter.AppListOnClickListener;
import com.appallure.miny.inter.PageChangeListener;
import com.appallure.miny.model.App;
import com.appallure.miny.util.AppListAdapter;
import com.appallure.miny.util.AppListUtil;
import com.appallure.miny.util.SortAppsByName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AllApps extends Fragment implements AppListOnClickListener, PageChangeListener {
    private static final String TAG = "AllApps";
    private EditText searchInput;
    private AppListAdapter appListAdapter;
    //private View view;

    public AllApps() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.pageChangeListener = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        searchInput.setText("");
        appListAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_apps, container, false);
        //this.view = view;
        searchInput = view.findViewById(R.id.et_search_app);

        List<App> appsList = AppListUtil.getAppsList(getContext());
        Collections.sort(appsList, new SortAppsByName());

        RecyclerView appsRecyclerView = view.findViewById(R.id.rv_app_list);
        appsRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        appsRecyclerView.setLayoutManager(layoutManager);

        appListAdapter = new AppListAdapter(appsList, this);
        appsRecyclerView.setAdapter(appListAdapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

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
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    protected void launchApp(String packageName) {
        Intent mIntent = getContext().getPackageManager().getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            try {
                getContext().startActivity(mIntent);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.viewPager.setCurrentItem(0, true);
            } catch (ActivityNotFoundException err) {
                Toast t = Toast.makeText(getContext().getApplicationContext(), "App was not found!", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    @Override
    public void onClick(String packageName, String appName) {
        launchApp(packageName);
    }

    @Override
    public void onLongClick(final String packageName, String appName) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(appName).setCancelable(true).setPositiveButton("App Info", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent appInfoIntent = new Intent();
                appInfoIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", packageName, null);
                appInfoIntent.setData(uri);
                startActivity(appInfoIntent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }

    @Override
    public void onPageChanged(int position) {
        Log.i("pageChanged", "Current position" + String.valueOf(position));
        if(position == 1){
            Log.i("pageChanged", "Keyboard show");
            searchInput.setText("");
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            imm.showSoftInput(searchInput, InputMethodManager.SHOW_FORCED);
            searchInput.requestFocus();
        }else if (position == 0){
            Log.i("pageChanged", "Keyboard hide");
            // hide the keyboard
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = getActivity().getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if(view != null){
                view.clearFocus();
            }
            if (view == null) {
                view = new View(getActivity());
                view.clearFocus();
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


            //InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}