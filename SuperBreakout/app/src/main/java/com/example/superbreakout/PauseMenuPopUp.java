package com.example.superbreakout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

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

        final Button mainMenuButton = findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PauseMenuPopUp.this, SuperBreakoutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        final Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //settings (for Steven)
                startActivity(new Intent(PauseMenuPopUp.this, SettingsMenuPopUp.class));
                finish();
            }
        });

        final Button leaderboardButton = findViewById(R.id.leaderboardButton);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //retrieve leaderboard
                Intent hsIntent = new Intent(PauseMenuPopUp.this, Leaderboard.class);
                startActivity(hsIntent);
            }
        });

        getWindow().setLayout((int) (width*.5), (int) (height*.8));
    }
}
