package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LevelOne extends Level {

    public static final int LEVEL_ONE = 1;

    public LevelOne(int x, int y, Context currentContext){
        super(x,y, currentContext);
        level = LEVEL_ONE;
        randomizer = new Randomizer();
    }

    @Override
    public void createBricks(Context context){
        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;

        // Build a wall of bricks and its potential debris
        int numBricks = 0;
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 3; row++) {
                bricks[numBricks] = new Obstacle(context, row, column, brickWidth, brickHeight);

                if (randomizer.getRandBoolean()) {
                    bricks[numBricks].setDurability(randomizer.getRandNumber(1,3));
                }
                else {
                    bricks[numBricks].setDurability(0);
                }
                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
    }

    @Override
    public Level advanceNextLevel(){
        return new LevelTwo(screenX, screenY, context);
    }
}
