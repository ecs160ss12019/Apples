package com.example.superbreakout;

/* Source:
 * https://github.com/PacktPublishing/Learning-Java-by-Building-Android-Games-Second-Edition/tree/master/Chapter11
 *
 * This class handles the view of the app.
 * Code based on Pong game by Packt Publishing.
 *
 * Code also based on StackOverflow:
 * https://stackoverflow.com/questions/15689404/onactivityresult-method-not-being-called-android
 * https://stackoverflow.com/questions/41295747/startactivityforresult-is-not-returning-data-to-parent-activity
 * https://stackoverflow.com/questions/22553672/android-startactivityforresult-setresult-for-a-view-class-and-an-activity-cla
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuperBreakoutActivity extends Activity {

    private GameView superBreakoutGame;
    HashMap <String, Integer> indicators;

    private int LevelIndicator = 0;
    private int SlideIndicator = 0;

    private static final int REQUEST_CODE_FOR_SETTINGS = 2; // code to confirm result returned from settings intent
    private static final int REQUEST_CODE_FOR_LEADERBOARD = 1; // code to confirm result returned from leaderboard intent

    FrameLayout game;
    private SharedPreferences hiScores;
    public static final String HI_SCORES = "HSFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        indicators = new HashMap<>();
        indicators.put("LevelIndicator", 1);
        indicators.put("SlideIndicator", 0);

        hiScores = getSharedPreferences(HI_SCORES, 0);

        superBreakoutGame = new GameView(this, size.x, size.y, hiScores);
        game = new FrameLayout(this); // adds a frame to enclose superBreakoutGame
        game.addView(superBreakoutGame); // adds superBreakoutGame surfaceView to the frame

        final RelativeLayout mainLayout = (RelativeLayout) View.inflate(this, R.layout.pause_ui, null);
        mainLayout.addView(game);

        setContentView(R.layout.activity_main);

        /**
         * Starts the background music service
         */
        Intent musicService = new Intent(SuperBreakoutActivity.this, BackgroundMusic.class);
        startService(musicService);

        /**
         * Creates a listener for the button so everytime the button is clicked, it runs this piece of code
         */
         final Button StartGame = findViewById(R.id.startGame);
        StartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /**
                 * Sets the indicators for the game
                 */
                superBreakoutGame.levelIndicator = indicators.get("LevelIndicator");
                superBreakoutGame.slideIndicator = indicators.get("SlideIndicator");

                Intent inputNickname = new Intent(SuperBreakoutActivity.this, NicknameInput.class);
                startActivityForResult(inputNickname, REQUEST_CODE_FOR_LEADERBOARD);

                // Code here executes on main thread after user presses button
                superBreakoutGame.startNewGame();
                setContentView(mainLayout);
            }
        });

        final Button setLevel = findViewById(R.id.level);
        setLevel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent LevelMenu = new Intent(SuperBreakoutActivity.this, LevelMenu.class);
                startActivityForResult(LevelMenu, REQUEST_CODE_FOR_SETTINGS);
            }
        });

        final Button leaderboard = findViewById(R.id.leaderboard);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ldrbrd = new Intent(SuperBreakoutActivity.this, Leaderboard.class);
                startActivity(ldrbrd);
            }
        });

    }

    public void onBackPressed(){
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.item1:
                //your action
                break;
            case R.id.item2:
                //your action
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void togglePausePlay(View view) {

        if(superBreakoutGame.playing) {
            superBreakoutGame.pause();
            startActivity(new Intent(SuperBreakoutActivity.this, PauseMenuPopUp.class));
        } else {
            superBreakoutGame.resume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        superBreakoutGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        superBreakoutGame.pause();
    }

    /**
     * Receives code from settings menu
     * Sets the level and slide indicator for the game to determine sliding or levels
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // super is needed for code to run

        if(requestCode == REQUEST_CODE_FOR_SETTINGS) {
            if (resultCode == RESULT_OK) {
                indicators.put("LevelIndicator", data.getIntExtra("LI", 1));
                indicators.put("SlideIndicator", data.getIntExtra("SI", 0));
            }
        }

        if(requestCode == REQUEST_CODE_FOR_LEADERBOARD) {
            if(resultCode == RESULT_OK) {
                superBreakoutGame.player.name = data.getStringExtra("NickName");
            }
        }

    }
}
