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
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuperBreakoutActivity extends Activity {

    private GameView superBreakoutGame;
    private int LevelIndicator = 0;
    FrameLayout game;
    private SharedPreferences hiScores;
    public static final String HI_SCORES = "HSFile";

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
        hiScores = getSharedPreferences(HI_SCORES, 0);

        /**
         * Gets intent from level
         */
        Intent receiveLevelMenu = getIntent();
        LevelIndicator =  receiveLevelMenu.getIntExtra("LevelIndicator",LevelIndicator);

        // Creates a listener for the button so everytime the button is clicked, it runs this piece of code
        final Button StartGame = findViewById(R.id.startGame);
        StartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inputNickname = new Intent(SuperBreakoutActivity.this, NicknameInput.class);
                startActivityForResult(inputNickname, 1);

                //return player name to superBreakoutGame


                superBreakoutGame.levelIndicator = LevelIndicator;
                // Code here executes on main thread after user presses button
                superBreakoutGame.startNewGame();
                setContentView(mainLayout);
            }
        });

        final Button setLevel = findViewById(R.id.level);
        setLevel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent LevelMenu = new Intent(SuperBreakoutActivity.this, LevelMenu.class);
                LevelMenu.putExtra("LevelIndicator", LevelIndicator);
                startActivity(LevelMenu);
            }
        });

    }


    private void setHighScore() {
        int currentScore = superBreakoutGame.player.getScore();

        SharedPreferences.Editor scoreEditor = hiScores.edit();
        String scores = hiScores.getString("highScores", "");

        if(scores.length() > 0) {
            // there are existing scores
            List<Score> scoreStrings = new ArrayList<Score>();
            String[] exScores = scores.split("\\|"); // Split strings

            // Add scores to the list in specified format
            for(String eSc : exScores) {
                String[] parts = eSc.split(" - ");
                scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
            }

            // Make a new score object with current player's score
            Score newScore = new Score(superBreakoutGame.player.name, currentScore);
            scoreStrings.add(newScore);

            //Sort scores
            Collections.sort(scoreStrings);

            StringBuilder scoreString = new StringBuilder("");
            for(int i = 0; i < scoreStrings.size(); i++) {
                if(i >= 5) break; // we only store top 5 scores
                if(i > 0) scoreString.append("|"); // separate different high scores
                scoreString.append(scoreStrings.get(i).getScoreText());
            }

            scoreEditor.putString("highScores", scoreString.toString());

        }
        else {
            // There are no existing scores
            scoreEditor.putString("highScores", "" + superBreakoutGame.player.name + " - " + currentScore);
        }

        scoreEditor.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                superBreakoutGame.player.name = data.getStringExtra("NickName");
            }
        }
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
}
