package com.appallure.miny.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.appallure.miny.MainActivity;
import com.appallure.miny.R;
import com.appallure.miny.service.ScreenOffService;

public class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
    private Context context;

    public SimpleGestureListener(Context context){
        this.context = context;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i("doubletap", "doubletapped");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if(ScreenOffService.instance != null){
                ScreenOffService.instance.turnScreenOff();
            } else {
                Toast.makeText(context, "Enable accessibility service miny from accessibility menu", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
