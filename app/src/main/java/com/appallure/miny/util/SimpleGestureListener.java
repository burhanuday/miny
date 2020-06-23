package com.appallure.miny.util;

import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.appallure.miny.service.ScreenOffService;

public class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {

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
                //Toast toast = Toast.makeText(e.get, "Enable accessibility service miny from accessibility menu", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
