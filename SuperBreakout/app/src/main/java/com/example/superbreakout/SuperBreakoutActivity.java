package com.example.superbreakout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.graphics.drawable.Drawable;

public class SuperBreakoutActivity extends Activity {

    private GameView superBreakoutGame;
    FrameLayout game;
    RelativeLayout GameButtons;//Holder for the buttons
    Button pauseButton, playButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        superBreakoutGame = new GameView(this, size.x, size.y);
        game = new FrameLayout(this); // adds a frame to enclose superBreakoutGame
        game.addView(superBreakoutGame); // adds superBreakoutGame surfaceView to the frame

        final RelativeLayout layout = (RelativeLayout) View.inflate(this, R.layout.pause_ui, null);
        pauseButton = findViewById(R.id.pauseButton);
        /*pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePausePlay(v);
            }
        });*/
        layout.addView(game);

        setContentView(R.layout.activity_main);

        // Creates a listener for the button so everytime the button is clicked, it runs this piece of code
        final Button button = findViewById(R.id.startGame);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                setContentView(layout);
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

        if(superBreakoutGame.paused) {
            Drawable playIcon = getResources().getDrawable(R.drawable.ic_play_button);
            pauseButton.setBackgroundDrawable(playIcon);
        } else {
            Drawable pauseIcon = getResources().getDrawable(R.drawable.ic_pause_button);
            pauseButton.setBackgroundDrawable(pauseIcon);
        }
        superBreakoutGame.paused = !superBreakoutGame.paused;
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
