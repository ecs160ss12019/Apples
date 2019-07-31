package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

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
    Context context;

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
        for (int i = 0; i < bricksInLevel; i++) {
            if (debris[i].getActive()) {
                canvas.drawBitmap(debris[i].getDebrisBitmap(),
                        null,
                        debris[i].getRect(),
                        paint);
            }
        }
    }

    public void initializeExplosion() {
        for (int i = 0; i < bricksInLevel; i++) {
            if (bricks[i] instanceof Explosive) {
                bricks[i].setNeighbors(bricks, rowsInLevel, columnsInLevel);
            }
        }
    }

    public void createPocket(int colStart, int rowStart, int rowsInLevel, Obstacle[] bricks, int width, int height) {
        for (int column = colStart; column < colStart + width; column++) {
            for (int row = rowStart; row < rowStart + height; row++) {
                bricks[column * rowsInLevel + row].setInvisible();
            }
        }
    }

    public void ballObstacleCollision(Ball ball, RectF obstacle) {

        RectF ballRect = ball.getRect();

        //colliding on the left side of obstacle
        if ((ballRect.right <= obstacle.left) && (ball.xVelocity > 0)) {
            if ((ballRect.bottom >= obstacle.top) || (ballRect.top <= obstacle.bottom)) {
                ball.reverseXVelocity();
                ball.clearObstacleX(obstacle.left - ball.width);
            }
        }
        //colliding on the right side of obstacle
        else if ((ballRect.left >= obstacle.right) && (ball.xVelocity < 0)) {
            if ((ballRect.bottom >= obstacle.top) || (ballRect.top <= obstacle.bottom)) {
                ball.reverseXVelocity();
                ball.clearObstacleX(obstacle.right);
            }
        }
        //colliding on the top side of the obstacle
        else if ((ballRect.bottom >= obstacle.top) && (ball.yVelocity > 0)) {
            if ((ballRect.right >= obstacle.left) || (ballRect.left <= obstacle.right)) {
                ball.reverseYVelocity();
                ball.clearObstacleY(obstacle.top);
            }
        } else if ((ballRect.top <= obstacle.bottom) && (ball.yVelocity < 0)) {
            if ((ballRect.right >= obstacle.left) || (ballRect.left <= obstacle.right)) {
                ball.reverseYVelocity();
                ball.clearObstacleY(obstacle.bottom + ball.height);
            }
        }
    }

    public boolean checkCollision(Ball ball) {
        boolean hit = false;
        // Check for ball colliding with a brick

        // Check if ball has hollow ball downgrade

        if (ball.hollow) {
            return hit;
        }

        if (ball.getActive()) {
            hit = checkActiveCollision(ball);
        } else {
            checkInActiveCollision(ball);
        }
        return hit;
    }

    private void checkInActiveCollision(Ball ball) {
        for (int i = 0; i < bricksInLevel; i++) {
            if (bricks[i].getVisibility()) {
                if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                    ballObstacleCollision(ball, bricks[i].getRect());
                }
            }
        }
    }

    private boolean checkActiveCollision(Ball ball) {
        boolean hit = false;
        for (int i = 0; i < bricksInLevel; i++) {
            if (bricks[i].getVisibility()) {
                if (RectF.intersects(bricks[i].getRect(), ball.getRect())
                        || ball.getRect().intersect(bricks[i].getRect()) || bricks[i].getRect().intersect(ball.getRect())) {
                    FX.playFX();

                    if (bricks[i].getDurability() == 0) {
                        bricks[i].setInvisible();
                        hitObstacle();

                        // Checks if there ball has an explosion upgrade
                        if (ball.explosion) {
                            // destroy left/right neighboring obstacles
                            explodingBall(i);
                        }

                        if (!debris[i].getDebrisType().equals("None")) {
                            debris[i].activate();
                        }

                    } else {
                        bricks[i] = bricks[i].reduceDurability();
                    }
                    ballObstacleCollision(ball, bricks[i].getRect());
                    hit = true;
                }
            }
        }
        return hit;
    }

    public void checkDebrisCollision(Bat bat, Player player) {
        for (int i = 0; i < bricksInLevel; i++) {
            for (int j = 0; j < ballsInLevel; j++) {
                if (debris[i].getActive()) {
                    if (RectF.intersects(debris[i].getRect(), balls[j].getRect())) {
                        // Checks ball/debris collision
                        if (balls[j].getActive()) {
                            debris[i].deactivate();
                        }
                    } else if (RectF.intersects(debris[i].getRect(), bat.getRect())) {
                        // Checks ball/bat debris collision

                        // receive effect based on debris type
                        switch (debris[i].getDebrisType()) {
                            case "Harmful":
                                // do something
                                bat.stun();
                                break;
                            case "Upgrade":
                                Upgrade ugs = new Upgrade();
                                if (player.addUpgrade(ugs)) {
                                    applyUpgrade(ugs, balls[0], bat);
                                }
                                break;
                            case "Downgrade":
                                Downgrade dgs = new Downgrade();
                                if (player.addDowngrade(dgs)) {
                                    applyDowngrade(dgs, balls[0], bat);
                                }
                                break;
                        }

                        debris[i].deactivate();
                    }
                }
            }
        }
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
            }
            balls[i].checkWallBounce();

        }

        updateDebris(fps);
        checkDebrisCollision(bat, player);
        player.updateEffects(bat, balls[0]);
        if (level == LevelFive.LEVEL_FIVE) {
            levelFiveFallBlock(bat,player);

        }
    }

    // Level Five fall block feature where the block slowly gets closer to the paddle
    private void levelFiveFallBlock(Bat bat, Player player) {
        for (Obstacle brick : bricks) {

            RectF rect = brick.getRect();
            rect.bottom += 1;
            rect.top += 1;
            if(levelFiveBrickBelow(brick,bat,player)){ break;}
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

    protected boolean levelFiveBrickBelow(Obstacle brick, Bat bat, Player player){
        RectF rect = brick.getRect();
        if(rect.bottom >= bat.getRect().top){
            player.missBrick(); // Reset consecutive hits
            player.reduceLifeByOne();
            return true;
        }
        return false;
    }

    protected void checkMissBall(Ball ball, Player player) {
        if (ball.checkMissBall()) {
            player.missBrick(); // Reset consecutive hits
            ball.playerMissedBall();

            if (!atLeastOneBallAlive()) {
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

    public void resetEffects(Player player, Bat bat) {
        player.clearEffects(bat, balls[0]);
    }

    public void createBalls(Context context, int screenX, int screenY) {
        balls = new Ball[ballsInLevel];
        balls[0] = new Ball(context, screenX, screenY);
        resetLevel();
    }

    private void hitObstacle() {
        numAliveBricks--;
    }

    public boolean levelCompleted() {
        if (numAliveBricks == 0) return true;
        else return false;
    }

    public int getLevel() {
        return level;
    }

    private void updateDebris(long fps) {
        // Updates the position of all active debris
        for (int i = 0; i < bricksInLevel; i++) {
            if (debris[i].getActive()) {
                debris[i].update(fps, screenY);
            }
        }
    }

    private void applyUpgrade(Upgrade ug, Ball ball, Bat bat) {
        switch (ug.getEffectTarget()) {
            case "Ball":
                ball.applyUpgrade(ug.upgradeName);
                break;
            case "Bat":
                bat.applyUpgrade(ug.upgradeName);
                break;
        }
    }

    private void applyDowngrade(Downgrade dg, Ball ball, Bat bat) {
        switch (dg.getEffectTarget()) {
            case "Ball":
                ball.applyDowngrade(dg.downgradeName);
                break;
            case "Bat":
                bat.applyDowngrade(dg.downgradeName);
                break;
        }
    }

    private void explodingBall(int index) {

        if (index < (rowsInLevel * columnsInLevel) &&
                index >= (rowsInLevel * columnsInLevel - rowsInLevel)) {
            // Bricks on the right-most column

            // Checks if there's a brick/pocket there
            if (bricks[index - rowsInLevel].getVisibility()) {
                bricks[index - rowsInLevel].setInvisible();
                hitObstacle();
            }
        } else if (index >= 0 && index < rowsInLevel) {
            // Bricks on the left-most column

            //Checks if there's a brick/pocket there
            if (bricks[index + rowsInLevel].getVisibility()) {
                bricks[index + rowsInLevel].setInvisible();
                hitObstacle();
            }
        } else {
            // Everything else in between
            if (bricks[index + rowsInLevel].getVisibility()) {
                bricks[index + rowsInLevel].setInvisible();
                hitObstacle();
            }

            if (bricks[index - rowsInLevel].getVisibility()) {
                bricks[index - rowsInLevel].setInvisible();
                hitObstacle();
            }
        }

    }

    abstract void createBricks(Context context);

    abstract Level advanceNextLevel();
}
