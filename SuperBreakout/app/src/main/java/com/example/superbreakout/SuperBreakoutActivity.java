package com.example.superbreakout;

import android.content.Intent;
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

public class SuperBreakoutActivity extends Activity {

    private GameView superBreakoutGame;
    private int LevelIndicator = 0;
    private int SlideIndicator = 0;
    private static final int REQUEST_CODE = 1; // code to confirm result returned from intent

    FrameLayout game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        superBreakoutGame = new GameView(this, size.x, size.y);
        game = new FrameLayout(this); // adds a frame to enclose superBreakoutGame
        game.addView(superBreakoutGame); // adds superBreakoutGame surfaceView to the frame

        final RelativeLayout mainLayout = (RelativeLayout) View.inflate(this, R.layout.pause_ui, null);
        mainLayout.addView(game);

        setContentView(R.layout.activity_main);

        Intent musicService = new Intent(SuperBreakoutActivity.this, BackgroundMusic.class);
        startService(musicService);

        // Creates a listener for the button so everytime the button is clicked, it runs this piece of code
        final Button StartGame = findViewById(R.id.startGame);
        StartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /**
                 * Sets the indicators for the game
                 */
                superBreakoutGame.levelIndicator = LevelIndicator;
                superBreakoutGame.slideIndicator = SlideIndicator;

                // Code here executes on main thread after user presses button
                superBreakoutGame.startNewGame();
                setContentView(mainLayout);
            }
        });

        final Button setLevel = findViewById(R.id.level);
        setLevel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent LevelMenu = new Intent(SuperBreakoutActivity.this, LevelMenu.class);
                startActivityForResult(LevelMenu, REQUEST_CODE);
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

        if(requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                this.LevelIndicator = data.getIntExtra("LI", 1);
                this.SlideIndicator = data.getIntExtra("SI", 0);
            }
        }
    }
}
