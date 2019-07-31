package com.example.superbreakout;

import android.content.Context;

public class LevelFour extends Level{

    public static final int LEVEL_FOUR = 4;
    public static final int BALLS_IN_LEVEL = 3;

    public LevelFour(int x, int y, Context currentContext){
        super(x,y, currentContext);
        level = LEVEL_FOUR;
        ballsInLevel = BALLS_IN_LEVEL;
    }

    @Override
    public void createBricks(Context context){
        DurabilityFactory durabilityFactory = new DurabilityFactory();
        int brickWidth = screenX / 12;
        int brickHeight = screenY / 20;
        bricksInLevel = 44;
        rowsInLevel = 4;
        columnsInLevel = bricksInLevel / rowsInLevel;
        bricks = new Obstacle[bricksInLevel];
        debris = new Debris[bricksInLevel];
        numAliveBricks = 30;

        // Build a wall of bricks and its potential debris
        int numBricks = 0;
        for (int column = 0; column < columnsInLevel; column++) {
            for (int row = 0; row < rowsInLevel; row++) {
                int rand = 1;
                if(Randomizer.getRandBoolean()) {
                    rand = Randomizer.getRandNumber(-1,2);
                }
                bricks[numBricks] = durabilityFactory.getDurabilityObject(context, row,
                        column, brickWidth, brickHeight,
                        brickWidth/2, brickHeight/3,rand);

                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(context, row , column, brickWidth, brickHeight, brickWidth/2, brickHeight/3);
                String[] types = {"Harmful", "Upgrade", "Downgrade", "None", "None", "None"};
                debris[numBricks].setDebrisType(types);
                numBricks++;
            }
        }

        this.createPocket(1,1, rowsInLevel, bricks, 2,2);
        this.createPocket(8,1, rowsInLevel, bricks, 2,2);
        this.createPocket(4,1, rowsInLevel, bricks, 3, 2);

        this.initializeExplosion();
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

        balls[1].setBallLocation(350,300,level);
        balls[2].setBallLocation(1025,300,level);
        balls[3].setBallLocation(750,300,level);

    }
}
