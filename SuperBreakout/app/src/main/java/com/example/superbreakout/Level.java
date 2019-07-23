package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.SoundPool;

public abstract class Level {

    private final int BRICKS_IN_LEVEL = 24;

    int screenX;
    int screenY;
    int numAliveBricks;
    int level = 0;
    Context context;
    Obstacle[] bricks = new Obstacle[BRICKS_IN_LEVEL];
    Debris[] debris = new Debris[BRICKS_IN_LEVEL];

    Randomizer randomizer;
    SoundEffects FX;
    SoundPool sp;

    public Level(int x, int y, Context currentContext){
        screenX = x;
        screenY = y;
        numAliveBricks = BRICKS_IN_LEVEL;
        context = currentContext;

        FX = new SoundEffects(context);
    }

    public void createBricks(Context context) {
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
        /*
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
        }*/
    }

    public boolean checkCollision(Ball ball){
        boolean hit = false;
        // Check for ball colliding with a brick
        for (int i = 0; i < BRICKS_IN_LEVEL; i++) {
            if (bricks[i].getVisibility()) {
                if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                    FX.playFX();

                    if(bricks[i].getDurability() == 0) {
                        bricks[i].setInvisible();
                        hitObstacle();
                    }
                    else {
                        bricks[i].reduceDurability();
                    }

                    if(!debris[i].getDebrisType().equals("None")) {
                        debris[i].activate();
                    }
                    ball.reverseYVelocity();
                    hit = true;
                }
            }
        }
        return hit;
    }

    public Level advanceNextLevel(){
        return new LevelOne(screenX, screenY, context);
    }

    public boolean levelCompleted(){
        if(numAliveBricks == 0 ) return true;
        else return false;
    }

    public int getLevel(){ return level;}

    private void hitObstacle(){ numAliveBricks--;}
}
