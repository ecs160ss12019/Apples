package com.example.superbreakout;

import android.content.Context;
import com.example.superbreakout.Obstacle;
import com.example.superbreakout.Level;
import com.example.superbreakout.LevelFour;

public class LevelThree extends Level{

    public static final int LEVEL_THREE = 3;
    public LevelThree(int x, int y, Context currentContext){
        super(x,y, currentContext);
        level = LEVEL_THREE;
        randomizer = new Randomizer();
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
                if(randomizer.getRandBoolean()) {
                   rand = randomizer.getRandNumber(-1,2);
                }
                bricks[numBricks] = durabilityFactory.getDurabilityObject(context, row,
                        column, brickWidth, brickHeight,
                        brickWidth/2, brickHeight/3,rand);

                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }

        this.createPocket(1,1, rowsInLevel, bricks, 2,2);
        this.createPocket(8,1, rowsInLevel, bricks, 2,2);
        this.createPocket(4,1, rowsInLevel, bricks, 3, 2);

        this.initializeExplosion();
    }

    private void createPocket(int colStart, int rowStart, int rowsInLevel, Obstacle[] bricks, int width, int height) {
        for(int column = colStart; column < colStart+width; column++) {
            for (int row = rowStart; row < rowStart+height; row++) {
                bricks[column * rowsInLevel + row].setInvisible();
            }
        }
    }

    @Override
    public Level advanceNextLevel(){
        // Add Win screen and create Level one again.
        return new LevelFour(screenX, screenY, context);
    }
}
