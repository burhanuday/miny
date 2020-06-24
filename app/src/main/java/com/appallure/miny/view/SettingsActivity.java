package com.appallure.miny.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.appallure.miny.R;

public class SettingsActivity extends AppCompatActivity {

    TextView setDefaultLauncher, enableDoubleTap;

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
}