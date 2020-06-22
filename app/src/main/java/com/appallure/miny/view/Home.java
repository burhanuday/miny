package com.appallure.miny.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appallure.miny.R;

public class Home extends Fragment {

    TextView shortcut1, shortcut2, shortcut3, shortcut4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        shortcut1 = view.findViewById(R.id.shortcut1);
        shortcut2 = view.findViewById(R.id.shortcut2);
        shortcut3 = view.findViewById(R.id.shortcut3);
        shortcut4 = view.findViewById(R.id.shortcut4);

        shortcut1.setOnClickListener(clickListener);
        shortcut2.setOnClickListener(clickListener);
        shortcut3.setOnClickListener(clickListener);
        shortcut4.setOnClickListener(clickListener);

        shortcut1.setOnLongClickListener(longClickListener);
        shortcut2.setOnLongClickListener(longClickListener);
        shortcut3.setOnLongClickListener(longClickListener);
        shortcut4.setOnLongClickListener(longClickListener);

        return view;
    }

    public View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;
            String text = textView.getText().toString();

            if(text.equals("select")){
                // open app picker here
            }else {
                // find the app using package name
            }
        }
    };

    public View.OnLongClickListener longClickListener = new View.OnLongClickListener(){

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    };
}