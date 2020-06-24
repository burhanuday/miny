package com.appallure.miny.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.appallure.miny.MainActivity;
import com.appallure.miny.R;

public class SettingsActivity extends AppCompatActivity {

    TextView setDefaultLauncher, enableDoubleTap, select1, select2, select3, select4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.settings_activity);

        setDefaultLauncher = findViewById(R.id.tv_set_default_launcher);
        enableDoubleTap = findViewById(R.id.tv_enable_double_tap);
        select1 = findViewById(R.id.tv_select_1);
        select2 = findViewById(R.id.tv_select_2);
        select3 = findViewById(R.id.tv_select_3);
        select4 = findViewById(R.id.tv_select_4);

        select1.setOnClickListener(listener);
        select2.setOnClickListener(listener);
        select3.setOnClickListener(listener);
        select4.setOnClickListener(listener);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            enableDoubleTap.setVisibility(View.GONE);
        }

        setDefaultLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    final Intent intent = new Intent(Settings.ACTION_HOME_SETTINGS);
                    startActivity(intent);
                }
                else {
                    final Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            }
        });

        enableDoubleTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Enable miny accessibility service", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2) {
            // app has been picked in app picker
            Home home = Home.getInstance();
            home.setUpShortcuts();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_select_1:
                    launchAppPicker("shortcut1");
                    break;
                case R.id.tv_select_2:
                    launchAppPicker("shortcut2");
                    break;
                case R.id.tv_select_3:
                    launchAppPicker("shortcut3");
                    break;
                case R.id.tv_select_4:
                    launchAppPicker("shortcut4");
                    break;
            }
        }
    };

    private void launchAppPicker(String shortcutNumber){
        Log.i("shortcutNum", "from launcher" + shortcutNumber);
        Bundle bundle = new Bundle();
        bundle.putString("shortcutNumber", shortcutNumber);
        Intent appPickerIntent = new Intent(this, AppPicker.class);
        appPickerIntent.putExtras(bundle);
        startActivityForResult(appPickerIntent, 2);
    }
}