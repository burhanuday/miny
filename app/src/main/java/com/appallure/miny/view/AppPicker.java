package com.appallure.miny.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.appallure.miny.R;
import com.appallure.miny.inter.AppListOnClickListener;
import com.appallure.miny.model.App;
import com.appallure.miny.util.AppListAdapter;
import com.appallure.miny.util.AppListUtil;
import com.appallure.miny.util.SortAppsByName;

import java.util.Collections;
import java.util.List;

public class AppPicker extends AppCompatActivity implements AppListOnClickListener {
    private SharedPreferences sharedPreferences;
    private String shortcutNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_app_picker);

        Bundle params = getIntent().getExtras();
        shortcutNumber = params.getString("shortcutNumber");
        Log.i("shortcutNum", shortcutNumber);

        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        List<App> appsList = AppListUtil.getAppsList(this);
        Collections.sort(appsList, new SortAppsByName());

        RecyclerView appsRecyclerView = findViewById(R.id.rv_app_picker);
        appsRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        appsRecyclerView.setLayoutManager(layoutManager);

        AppListAdapter appListAdapter = new AppListAdapter(appsList, this);
        appsRecyclerView.setAdapter(appListAdapter);
    }

    @Override
    public void onClick(String packageName, String appName) {
        Log.i("AppClicked", packageName + appName);
        sharedPreferences.edit().putString(shortcutNumber + "PackageName", packageName).apply();
        sharedPreferences.edit().putString(shortcutNumber + "AppName", appName).apply();

        Intent goBack = new Intent();
        setResult(2, goBack);
        finish();
    }

    @Override
    public void onLongClick(String packageName, String appName) {
        // do nothing
    }
}