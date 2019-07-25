package com.example.superbreakout;

import android.content.Context;
import com.example.superbreakout.Obstacle;
import com.example.superbreakout.Level;
import com.example.superbreakout.LevelFour;

public class LevelThree extends Level{

    public static final int LEVEL_THREE = 3;
    public static final int BALLS_IN_LEVEL = 2;

    public LevelThree(int x, int y, Context currentContext){
        super(x,y, currentContext);
        level = LEVEL_THREE;
        ballsInLevel = BALLS_IN_LEVEL;
        randomizer = new Randomizer();
        createBricks(context);
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
                int rand = 0;
                if(randomizer.getRandBoolean() && bricks[numBricks].getVisibility()) {
                   rand = randomizer.getRandNumber(1,2);
                }
                bricks[numBricks] = durabilityFactory.getDurabilityObject(context, row,
                        column, brickWidth, brickHeight,
                        brickWidth/4, brickHeight/5,rand);

                // can possibly change this to spawnDebris()

              //  debris[numBricks] = new Debris(row, column, brickWidth, brickHeight);
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
        // Add Win screen and create Level one again.
        return new LevelFour(screenX, screenY, context);
    }

    @Override
    public void createBalls(Context context, int screenX, int screenY){
        balls = new Ball[ballsInLevel];

        for (int i = 0; i < ballsInLevel; i++) {
            balls[i] = new Ball(context, screenX, screenY);
        }
        balls[0].makeActive();
        balls[0].reset(screenX, screenY, level);

       /*for(int i = 1; i < ballsInLevel; i++){
            balls[i] = new Ball(context, screenX, screenY);
            balls[i].reset(screenX,screenY,level);
        }*/
    }

}
