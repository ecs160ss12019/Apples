package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.SoundPool;

public abstract class Level {

    int bricksInLevel;
    int rowsInLevel;
    int columnsInLevel;
    int screenX;
    int screenY;
    int numAliveBricks;
    int level = 0;
    Obstacle[] bricks = new Obstacle[bricksInLevel];
    Debris[] debris = new Debris[bricksInLevel];
    Context context;

    Randomizer randomizer;
    SoundEffects FX;
    SoundPool sp;

    public Level(int x, int y, Context currentContext){
        screenX = x;
        screenY = y;

        context = currentContext;

        FX = new SoundEffects(context);
    }

    abstract void createBricks(Context context);

    public void draw(Canvas canvas, Paint paint){
        for (int i = 0; i < bricksInLevel; i++) {
            if (bricks[i].getVisibility()) {
                canvas.drawBitmap(bricks[i].getBricksBitmap(),
                        bricks[i].getRect().left,
                        bricks[i].getRect().top,
                        paint);
            }
        }

        // Draw the debris if active
        for(int i = 0; i < bricksInLevel; i++) {
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

    public boolean checkCollision(Ball ball){
        boolean hit = false;
        // Check for ball colliding with a brick
        for (int i = 0; i < bricksInLevel; i++) {
            if (bricks[i].getVisibility()) {
                if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                    FX.playFX();

                    if(bricks[i].getDurability() == 0) {
                        bricks[i].setInvisible();
                        hitObstacle();
                    }
                    else {
                        bricks[i] = bricks[i].reduceDurability();
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

    public void checkDebrisCollision(Ball ball, Bat bat) {
        for(int i = 0; i < bricksInLevel; i++) {
            if(debris[i].getActive()) {

                if(RectF.intersects(debris[i].getRect(), ball.getRect())) {
                    // Checks ball/debris collision
                    debris[i].deactivate();
                } else if (RectF.intersects(debris[i].getRect(), bat.getRect())) {
                    // Checks ball/bat debris collision

                    // receive effect based on debris type
                    switch(debris[i].getDebrisType()) {
                        case "Harmful":
                            // do something
                            break;
                        case "Upgrade":
                            // do something
                            break;
                        case "Downgrade":
                            // do something
                            break;
                    }

                    debris[i].deactivate();
                }
            }
        }
    }

    public Level advanceNextLevel(){
        return new LevelOne(screenX, screenY, context);
    }

    public boolean levelCompleted(){
        if(numAliveBricks == 0)
            return true;
        else
            return false;
    }

    public int getLevel(){ return level;}

    private void hitObstacle(){ numAliveBricks--;}

    public void updateDebris() {
        // Updates the position of all active debris
        for(int i = 0; i < bricksInLevel; i++) {
            if(debris[i].getActive()) {
                debris[i].update();
            }
        }
    }
}
