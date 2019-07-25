package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
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
    int ballsInLevel = 1;

    Ball[] balls;
    Obstacle[] bricks = new Obstacle[bricksInLevel];
    Debris[] debris = new Debris[bricksInLevel];
    Context context;

    Randomizer randomizer;
    SoundEffects FX;
    SoundPool sp;

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
                        bricks[i].getRect().left,
                        bricks[i].getRect().top,
                        paint);
            }
        }
    }

    public boolean checkCollision(Ball ball) {
        boolean hit = false;
        // Check for ball colliding with a brick

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
    }

    public Level advanceNextLevel() {
        return new LevelOne(screenX, screenY, context);
    }

    public boolean levelCompleted() {
        if (numAliveBricks == 0)
            return true;
        else
            return false;
    }

    public int getLevel() {
        return level;
    }

    private void hitObstacle() {
        numAliveBricks--;
    }

    public void update(long fps, Bat bat, Player player) {
        for (int i = 0; i < ballsInLevel; i++) {
            balls[i].update(fps);
            if (!balls[i].checkBallBatCollision(bat)) {// If bat didn't hit the ball
                if (checkCollision(balls[i]) && balls[i].getActive()) {
                    player.hitBrick();
                } else if (balls[i].getActive()) {
                    checkMissBall(balls[i], player);
                }
                balls[i].checkWallBounce();
            }
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

    private void checkMissBall(Ball ball, Player player) {

        boolean missBall = ball.checkMissBall();
        if (missBall) {
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

        for (int i = 0; i < ballsInLevel; i++) {
            balls[i] = new Ball(context, screenX, screenY);
        }
        balls[0].makeActive();
        balls[0].reset(screenX, screenY, level);

    }

    abstract void createBricks(Context context);
}
