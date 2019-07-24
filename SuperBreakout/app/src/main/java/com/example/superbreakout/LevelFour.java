package com.example.superbreakout;

import android.content.Context;

public class LevelFour extends Level{

    public static final int LEVEL_FOUR = 4;
    public LevelFour(int x, int y, Context currentContext){
        super(x,y, currentContext);
        level = LEVEL_FOUR;
        randomizer = new Randomizer();
    }

    @Override
    public void createBricks(Context context){
        DurabilityFactory durabilityFactory = new DurabilityFactory();
        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;
        bricksInLevel = 32;
        rowsInLevel = 4;
        columnsInLevel = bricksInLevel / rowsInLevel;
        bricks = new Obstacle[bricksInLevel];
        debris = new Debris[bricksInLevel];
        numAliveBricks = 24;

        // Build a wall of bricks and its potential debris
        int numBricks = 0;
        for (int column = 0; column < columnsInLevel; column++) {
            for (int row = 0; row < rowsInLevel; row++) {
                int rand = 1;
               /*if(randomizer.getRandBoolean() && bricks[numBricks].getVisibility()) {
                    rand = randomizer.getRandNumber(1,2);
                }*/
                bricks[numBricks] = durabilityFactory.getDurabilityObject(context, row,
                        column, brickWidth, brickHeight,
                        brickWidth/4, brickHeight/5,rand);

                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
        for (int row = 1; row < 3; row++) {
            bricks[1*rowsInLevel + row].setInvisible();
            bricks[6*rowsInLevel + row].setInvisible();
        }
        for(int column = 3; column < 5; column++) {
            for (int row = 1; row < 3; row++) {
                bricks[column * rowsInLevel + row].setInvisible();
            }
        }
    }

    @Override
    public Level advanceNextLevel(){
        // Add Win screen and create Level one again.
        return new LevelFive(screenX, screenY, context);
    }
}