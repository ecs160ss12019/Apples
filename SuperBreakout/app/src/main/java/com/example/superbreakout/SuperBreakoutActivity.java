package com.example.superbreakout;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class SuperBreakoutActivity extends Activity {
    private GameView superBreakoutGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        superBreakoutGame = new GameView(this, size.x, size.y);
        setContentView(superBreakoutGame);
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
