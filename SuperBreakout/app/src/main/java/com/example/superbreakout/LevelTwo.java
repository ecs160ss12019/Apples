package com.example.superbreakout;

import android.content.Context;

public class LevelTwo extends Level {

    public static final int LEVEL_TWO = 2;

    public LevelTwo(int x, int y){
        super(x,y);
        level = LEVEL_TWO;
    }

    @Override
    public void createBricks(Context context){
        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;
        bricksInLevel = 24;
        rowsInLevel = 3;
        columnsInLevel = bricksInLevel / rowsInLevel;
        bricks = new Obstacle[bricksInLevel];
        debris = new Debris[bricksInLevel];
        numAliveBricks = 24;

        // Build a wall of bricks and its potential debris
        int numBricks = 0;
        for (int column = 0; column < columnsInLevel; column++) {
            for (int row = 0; row < rowsInLevel; row++) {
                bricks[numBricks] = new Obstacle(context, row, column, brickWidth, brickHeight,
                        brickWidth/5, brickHeight/4);
                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
    }


    @Override
    public Level advanceNextLevel(){
        return new LevelThree(screenX, screenY);
    }

}
