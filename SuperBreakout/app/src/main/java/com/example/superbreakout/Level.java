package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Level {

    private final int BRICKS_IN_LEVEL = 24;

    int screenX;
    int screenY;
    int numAliveBricks;
    int level = 0;
    Obstacle[] bricks = new Obstacle[BRICKS_IN_LEVEL];
    Debris[] debris = new Debris[BRICKS_IN_LEVEL];

    public Level(Context context, int x, int y){
        screenX = x;
        screenY = y;
        createBricks(context);
        numAliveBricks = BRICKS_IN_LEVEL;

    }

    public void createBricks(Context context) {
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

        // Draw the debris if active
        for(int i = 0; i < BRICKS_IN_LEVEL; i++) {
            if(debris[i].getActive()) {
                // Change paint color depending on debris type
                switch (debris[i].getDebrisType()) {
                    case "Harmful":
                        paint.setColor(Color.argb(255, 255, 0, 0));
                        break;
                    case "Upgrade":
                        paint.setColor(Color.argb(255, 0, 255, 0));
                        break;
                    case "Downgrade":
                        paint.setColor(Color.argb(255, 0, 0, 255));
                        break;
                    default:
                        break;
                }
                canvas.drawRect(debris[i].getRect(), paint);
            }
        }
    }

    public boolean levelCompleted(){
        if(numAliveBricks == 0 ) return true;
        else return false;
    }

    public int getLevel(){ return level;}
}
