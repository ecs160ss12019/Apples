package com.example.superbreakout;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class SettingsMenuPopUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);

        // set window size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        final Button slideOnButton = findViewById(R.id.slideOnButton);
        slideOnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        final Button settingsButton = findViewById(R.id.slideOffButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //settings (for Steven)
            }
        });

        getWindow().setLayout((int) (width*.5), (int) (height*.8));
    }
}
