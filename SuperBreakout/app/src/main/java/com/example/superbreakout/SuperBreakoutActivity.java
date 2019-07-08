package com.example.superbreakout;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class SuperBreakoutActivity extends Activity {
    private SuperBreakoutGame mSuperBreakoutGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mSuperBreakoutGame = new SuperBreakoutGame(this, size.x, size.y);
        setContentView(mSuperBreakoutGame);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // More code here later in the chapter
        mSuperBreakoutGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // More code here later in the chapter
        mSuperBreakoutGame.pause();
    }
}
