package com.example.superbreakout;

/**
 * Level layout for level two.
 */

import android.content.Context;

public class LevelTwo extends Level {

    public static final int LEVEL_TWO = 2;
    public static final int BALLS_IN_LEVEL = 1;

    public LevelTwo(int x, int y, Context currentContext){
        super(x,y, currentContext);
        level = LEVEL_TWO;
        ballsInLevel = BALLS_IN_LEVEL;
    }

    @Override
    public void createBricks(Context context){
        int brickWidth = screenX / 12;
        int brickHeight = screenY / 20;
        bricksInLevel = 33;
        rowsInLevel = 3;
        columnsInLevel = bricksInLevel / rowsInLevel;
        bricks = new Obstacle[bricksInLevel];
        debris = new Debris[bricksInLevel];
        numAliveBricks = 33;

        // Build a wall of bricks and its potential debris
        int numBricks = 0;
        for (int column = 0; column < columnsInLevel; column++) {
            for (int row = 0; row < rowsInLevel; row++) {
                bricks[numBricks] = new DurabilityZero(context, row, column, brickWidth, brickHeight,
                        brickWidth/2, brickHeight/3);
                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(context, row , column, brickWidth, brickHeight, brickWidth/2, brickHeight/3);
                String[] types = {"Harmful", "Upgrade", "Downgrade", "None"};
                debris[numBricks].setDebrisType(types);
                numBricks++;
            }
        }
        createBalls(context,screenX,screenY);

    }


    @Override
    public Level advanceNextLevel(){
        return new LevelThree(screenX, screenY, context);
    }

}
