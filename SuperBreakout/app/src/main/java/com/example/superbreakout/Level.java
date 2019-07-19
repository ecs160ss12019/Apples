package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Level {

    private final int BRICKS_IN_LEVEL = 24;
    int level = 1;
    int screenX;
    int screenY;
    int numAliveBricks;
    Obstacle[] bricks = new Obstacle[BRICKS_IN_LEVEL];
    Debris[] debris = new Debris[BRICKS_IN_LEVEL];

    public Level(Context context, int x, int y){
        screenX = x;
        screenY = y;
        createBricks(context, level);
        numAliveBricks = BRICKS_IN_LEVEL;

    }

    public void createBricks(Context context, int Xlevel) {

        // Put the ball back to the start
        //ball.reset(screenX, screenY, Xlevel);

        // Brick Size
        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;

        // Build a wall of bricks and its potential debris
        int numBricks = 0;
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 3; row++) {
                bricks[numBricks] = new Obstacle(context, row, column, brickWidth, brickHeight);
                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
    }

    public void draw(Canvas canvas, Paint paint){
        for (int i = 0; i < BRICKS_IN_LEVEL; i++) {
            if (bricks[i].getVisibility()) {
                canvas.drawBitmap(bricks[i].getBricksBitmap(),
                        bricks[i].getRect().left,
                        bricks[i].getRect().top,
                        paint);
            }
        }
    }

    public boolean levelCompleted(){
        if(numAliveBricks == 0 ) return true;
        else return false;
    }


}
