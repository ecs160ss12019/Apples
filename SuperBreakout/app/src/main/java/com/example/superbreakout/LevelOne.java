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
        int brickWidth = screenX / 12;
        int brickHeight = screenY / 20;
        bricksInLevel = 50;
        rowsInLevel = 5;
        columnsInLevel = bricksInLevel / rowsInLevel;
        bricks = new Obstacle[bricksInLevel];
        debris = new Debris[bricksInLevel];
        numAliveBricks = 20;

        // Build a wall of bricks and its potential debris
        int numBricks = 0;
        for (int column = 0; column < columnsInLevel; column++) {
            for (int row = 0; row < rowsInLevel; row++) {
                bricks[numBricks] = new DurabilityZero(context, row, column, brickWidth, brickHeight,
                        brickWidth, brickHeight/3);
                if(row == 1 || row == 2 || row == 3) {
                    bricks[numBricks].setInvisible();
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
