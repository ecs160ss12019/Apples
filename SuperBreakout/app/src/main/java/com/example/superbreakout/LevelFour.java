package com.example.superbreakout;

import android.content.Context;

public class LevelFour extends Level{

    public static final int LEVEL_FOUR = 4;
    public static final int BALLS_IN_LEVEL = 3;

    public LevelFour(int x, int y, Context currentContext){
        super(x,y, currentContext);
        level = LEVEL_FOUR;
        randomizer = new Randomizer();
        ballsInLevel = BALLS_IN_LEVEL;
    }

    @Override
    public void createBricks(Context context){
        DurabilityFactory durabilityFactory = new DurabilityFactory();
        int brickWidth = screenX / 12;
        int brickHeight = screenY / 20;
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
                int rand = 0;

                if(randomizer.getRandBoolean()) {
                    rand = randomizer.getRandNumber(1,4);
                }
                bricks[numBricks] = durabilityFactory.getDurabilityObject(context, row,
                        column, brickWidth, brickHeight,
                        brickWidth/4+screenX/7, brickHeight/5,rand);

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
        createBalls(context,screenX,screenY);

    }

    @Override
    public Level advanceNextLevel(){
        return new LevelFive(screenX, screenY, context);
    }

    @Override
    public void createBalls(Context context, int screenX, int screenY){
        balls = new Ball[ballsInLevel];

        for(int i =0; i<ballsInLevel; i++){
            balls[i] = new Ball(context, screenX, screenY);
            balls[i].reset(screenX,screenY,level);
        }
        balls[0].makeActive();
    }
}