package com.example.superbreakout;

/**
 * Level layout for level one.
 */

import android.content.Context;


public class LevelOne extends Level {

    public static final int LEVEL_ONE = 1;
    public static final int BALLS_IN_LEVEL = 1;

    public LevelOne(int x, int y, Context currentContext){
        super(x,y, currentContext);
        level = LEVEL_ONE;
        ballsInLevel = BALLS_IN_LEVEL;
    }

    @Override
    public void createBricks(Context context){
        DurabilityFactory durabilityFactory = new DurabilityFactory();

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
                bricks[numBricks] = durabilityFactory.getDurabilityObject(context, row, column, brickWidth, brickHeight,
                        brickWidth, brickHeight/3, 0);
                if(row == 1 || row == 2 || row == 3) {
                    bricks[numBricks].setInvisible();
                }
                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(context, row , column, brickWidth, brickHeight, brickWidth, brickHeight/3);
                String[] types = {"Harmful", "None", "None", "None"};
                debris[numBricks].setDebrisType(types);
                numBricks++;
            }
        }

        createBalls(context,screenX,screenY);
    }

    @Override
    public Level advanceNextLevel(){
        return new LevelTwo(screenX, screenY, context);
    }

}
