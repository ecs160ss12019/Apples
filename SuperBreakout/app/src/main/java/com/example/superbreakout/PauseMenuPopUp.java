package com.example.superbreakout;

/**
 * This class handles the UI that pops up when the pause button is pressed in the game.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class PauseMenuPopUp extends Activity {
    /**
     * Constructor of the UI of the pause menu,.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pause_popup_window);

        // set window size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        /**
         * Starts intent on click of the main menu button.
         */
        final Button mainMenuButton = findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PauseMenuPopUp.this, SuperBreakoutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        /**
         * Starts intent on click of the leaderboard button.
         */
        final Button leaderboardButton = findViewById(R.id.leaderboardButton);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //retrieve leaderboard
                Intent hsIntent = new Intent(PauseMenuPopUp.this, Leaderboard.class);
                hsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(hsIntent);

            }
        });

        /**
         * Dimensions of the UI.
         */
        getWindow().setLayout((int) (width*.5), (int) (height*.8));
    }
}
