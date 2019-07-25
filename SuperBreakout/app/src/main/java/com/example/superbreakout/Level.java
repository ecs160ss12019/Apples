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
    int ballsInLevel;
    Ball[] balls;
    Obstacle[] bricks = new Obstacle[bricksInLevel];
    Debris[] debris = new Debris[bricksInLevel];
//    Upgrade[] ug = new Upgrade[bricksInLevel];
//    Downgrade[] dg = new Downgrade[bricksInLevel];
    Context context;

    Randomizer randomizer;
    SoundEffects FX;

    public Level(int x, int y, Context currentContext) {
        screenX = x;
        screenY = y;

        context = currentContext;

        FX = new SoundEffects(context);
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < bricksInLevel; i++) {
            if (bricks[i].getVisibility()) {
                canvas.drawBitmap(bricks[i].getBricksBitmap(),
                        null,
                        bricks[i].getRect(),
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

    public void initializeExplosion() {
        for(int i = 0; i < bricksInLevel; i++) {
            if(bricks[i] instanceof Explosive) {
                bricks[i].setNeighbors(bricks, rowsInLevel, columnsInLevel);
            }
        }
    }

    public boolean checkCollision(Ball ball){
    public boolean checkCollision(Ball ball) {
        boolean hit = false;
        // Check for ball colliding with a brick
        for (int i = 0; i < bricksInLevel; i++) {
            if (bricks[i].getVisibility()) {
                if (RectF.intersects(bricks[i].getRect(), ball.getRect()) || ball.getRect().intersect(bricks[i].getRect()) || bricks[i].getRect().intersect(ball.getRect())) {
                    FX.playFX();

        if(ball.getActive()) {
            for (int i = 0; i < bricksInLevel; i++) {
                if (bricks[i].getVisibility()) {
                    if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                        FX.playFX();

                        if (bricks[i].getDurability() == 0) {
                            bricks[i].setInvisible();
                            hitObstacle();
                        } else {
                            bricks[i] = bricks[i].reduceDurability();
                        }

                        if (!debris[i].getDebrisType().equals("None")) {
                            debris[i].activate();
                        }
                        ball.reverseYVelocity();
                        hit = true;
                    }
                }
            }
            return hit;
        }else{
            for (int i = 0; i < bricksInLevel; i++) {
                if (bricks[i].getVisibility()) {
                    if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {

                    if(!debris[i].getDebrisType().equals("None")) {
                        debris[i].activate();

                        // BUG
//                        if(debris[i].getDebrisType().equals("Upgrade")) {
//                            ug[i] = new Upgrade();
//                        } else if(debris[i].getDebrisType().equals("Downgrade")) {
//                            dg[i] = new Downgrade();
//                        }

                        ball.reverseYVelocity();
                    }
                }
            }
        }
        return false;
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
                            bat.stun();
                            break;
                        case "Upgrade":
                            Upgrade ugs = new Upgrade();
                            applyUpgrade(ugs, ball, bat);
                            break;
                        case "Downgrade":
                            Downgrade dgs = new Downgrade();
                            applyDowngrade(dgs, ball, bat);
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


    private void hitObstacle() {
        numAliveBricks--;
    }

    public void update(long fps, Bat bat, Player player) {
        for (int i = 0; i < ballsInLevel; i++) {
            balls[i].update(fps);
            if (!balls[i].checkBallBatCollision(bat)) {// If bat didn't hit the ball
                if (checkCollision(balls[i])&& balls[i].getActive() ) {
                    player.hitBrick();
                } else if (balls[i].getActive()) {
                    checkMissBall(balls[i], player);
                }
            }
            balls[i].checkWallBounce();
        }
    }

    public boolean atLeastOneBallAlive() {
        for (Ball ball : balls) {
            if (ball.getActive()) {
                return true;
            }
        }
        return false;
    }

    protected void checkMissBall(Ball ball, Player player) {
        if (ball.checkMissBall()) {
            player.missBrick(); // Reset consecutive hits
            ball.playerMissedBall();

            if(!atLeastOneBallAlive()){
                player.reduceLifeByOne();
            }
        }
    }

    public void drawBall(Canvas canvas) {
        for (int i = 0; i < ballsInLevel; i++) {
            canvas.drawBitmap(balls[i].getBallBitmap(),
                    balls[i].getRect().left,
                    balls[i].getRect().top,
                    null);
        }
    }

    public void resetLevel() {
        balls[0].makeActive();
        balls[0].reset(screenX, screenY, level);
    }

    public void createBalls(Context context, int screenX, int screenY) {
        balls = new Ball[ballsInLevel];
        balls[0] = new Ball(context, screenX, screenY);
        resetLevel();
    }

    abstract void createBricks(Context context);

    abstract Level advanceNextLevel();

    private void hitObstacle(){ numAliveBricks--;}

    public void updateDebris() {
        // Updates the position of all active debris
        for(int i = 0; i < bricksInLevel; i++) {
            if(debris[i].getActive()) {
                debris[i].update();
            }
        }
    }

    private void applyUpgrade(Upgrade ug, Ball ball, Bat bat) {
        switch(ug.getEffectTarget()) {
            case "Ball":
                ball.applyUpgrade(ug.upgradeName);
                break;
            case "Bat":
                bat.applyUpgrade(ug.upgradeName);
                break;
        }
    }

    private void applyDowngrade(Downgrade dg, Ball ball, Bat bat) {
        switch(dg.getEffectTarget()) {
            case "Ball":
                ball.applyDowngrade(dg.downgradeName);
                break;
            case "Bat":
                bat.applyDowngrade(dg.downgradeName);
                break;
        }
    }
}
}
