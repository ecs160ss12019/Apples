package com.example.superbreakout;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PauseMenuPopUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pause_popup_window);

        // set window size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.8));
    }
}
