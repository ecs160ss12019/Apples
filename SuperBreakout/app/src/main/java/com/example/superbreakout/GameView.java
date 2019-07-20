package com.example.superbreakout;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import java.io.IOException;
import java.lang.Math;

public class GameView extends SurfaceView implements Runnable {

    Thread gameThread = null;
    SurfaceHolder ourHolder;
    Canvas canvas;
    Paint paint;
    long fps;

    volatile boolean playing;
    boolean paused = true;

    // Use Point class for this
    int screenX;
    int screenY;

    Bat bat;
    Ball ball;
    Obstacle[] bricks = new Obstacle[24];
    int numBricks = 0;
    Debris[] debris = new Debris[24];

    // Abstract this into a class, then set getter for this. Possibly setter (?)
    int score = 0;
    int level = 1;
    int lives = 3;

    Rect dest;
    DisplayMetrics dm;
    int densityDpi;

    /*
    SOUND FX FIXME
    SoundPool soundPool;
    int beep1ID = -1;
    int beep2ID = -1;
    int beep3ID = -1;
    int loseLifeID = -1;
    int explodeID = -1;
    */

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }


    public GameView(Context context, int x, int y) {
        super(context);

        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        dm = context.getResources().getDisplayMetrics();
        densityDpi = dm.densityDpi;

        bat = new Bat(context, screenX, screenY, densityDpi);
        ball = new Ball(context, screenX, screenY);

        // Create bricks for level 1
        createBricksAndRestart(1);
    }


    public void createBricksAndRestart(int Xlevel) {

        // Put the ball back to the start
        ball.reset(screenX, screenY, Xlevel);

        level = Xlevel;

        // Brick Size
        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;

        // Build a wall of bricks and its potential debris
        numBricks = 0;
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 3; row++) {
                bricks[numBricks] = new Obstacle(getContext(), row, column, brickWidth, brickHeight);
                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }

        // if Game is over reset scores ,lives &Level
        if (lives == 0) { restartGame();}

    }



    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();

            if (!paused) {
                update();
            }
            draw();

            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

        }

    }


    public void update() {

        bat.update(fps);
        ball.update(fps);
        updateDebris(fps);

        ballBrickCollision();
        debrisCollision();
        ballPaddleCollision();

        if(!checkMissBall()) {
            // Pause if cleared screen
            if (score == numBricks * 10) { // Move to level two
                succeedToLevelTwo();
            } else if (score == (numBricks * 20) + 10) { // Move to level three
                succeedToLevelThree();
            } else if (score == (numBricks * 10 * 3) + 20) { // Winning
                paused = true;
            } else {
                checkWallBounce();
            }
        }

    }

    // Draw the newly updated scene
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255, 153, 204, 255));

            dest = new Rect(0, 0, getWidth(), getHeight());

            // Choose the brush color for drawing white
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the ball
            // canvas.drawCircle(ball.getRect().centerX(), ball.getRect().centerY(), 25, paint);
            canvas.drawBitmap(ball.getBallBitmap(),ball.getRect().left,ball.getRect().top,paint);

            // sets brush color to red
            paint.setColor(Color.argb(255, 255, 0, 0));

            // Draw the paddle
            // canvas.drawRect(bat.getRect(), paint);
            canvas.drawBitmap(bat.getBatBitmap(), bat.getRect().left, bat.getRect().top, null);


            // Change the brush color for drawing
            // paint.setColor(getResources().getColor(R.color.redorange));

            // sets brush color to white
            paint.setColor(Color.argb(255, 255, 0, 0));

            // Draw the bricks if visible
            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getVisibility()) {
                    // canvas.drawRect(bricks[i].getRect(), paint);
                    canvas.drawBitmap(bricks[i].getBricksBitmap(), bricks[i].getRect().left, bricks[i].getRect().top, paint);
                    /*switch (level) {
                        case 1:
                            canvas.drawBitmap(bitmapBrick1, bricks[i].getRect().left, bricks[i].getRect().top, null);

                            break;

                        case 2:
                            canvas.drawBitmap(bitmapBrick2, bricks[i].getRect().left, bricks[i].getRect().top, null);

                            break;
                        case 3:
                            canvas.drawBitmap(bitmapBrick3, bricks[i].getRect().left, bricks[i].getRect().top, null);
                            break;
                    }*/


                }
            }

            // Draw the debris if active
            for(int i = 0; i < numBricks; i++) {
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

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(50);

            // Score Text
            canvas.drawText(
                    "Score: " + score
                    , densityDpi / 5, (screenY / 2) + (densityDpi / 1.50f), paint);

            // Lives Text
            canvas.drawText("Lives: " + lives
                    , densityDpi / 5, screenY / 2, paint);

            // Levels Text
            canvas.drawText("Level: " + level
                    , densityDpi / 5, screenY / 2 + (densityDpi / 3f), paint);

            // Has the player cleared the screen?
            if (score >= (numBricks * 10 * 3) + 20) {
                paint.setColor(getResources().getColor(R.color.colorAccent));
                canvas.drawText("You got home!", screenX / 2 - (densityDpi / 1.90f), screenY / 2 + (densityDpi / 1), paint);

            }

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

    // If GameActivity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    // If GameActivity is started
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: // Player has touched the screen
                if (!(lives == 0)){ paused = false;}
                bat.move(motionEvent.getX());
                break;

            case MotionEvent.ACTION_UP: // Player doesn't touch screen
                bat.stopMoving();
                break;
        }
        return true;
    }

    /************ HELPER FUNCTIONS ************/
    private void restartGame(){ score = 0; lives = 3;level =1; }

    private void updateDebris(long fps) {
        // Updates the position of all active debris
        for (int i = 0; i < numBricks; i++) {
            if(debris[i].getActive()) {
                debris[i].update(fps);
            }
        }
    }

    private void debrisCollision() {
        // Check for ball or bat colliding with a debris
        for(int i = 0; i < numBricks; i++) {
            if(debris[i].getActive()) {
                /*
                 hit-box of collision kinda confusing since sometimes the ball will go through it,
                 and debris will not disappear
                  */
                if(RectF.intersects(debris[i].getRect(), ball.getRect())) {
                    debris[i].deactivate();
                } else if(RectF.intersects(debris[i].getRect(), bat.getRect())) {
                    // receive effect
                    debris[i].deactivate();
                }
            }
        }
    }


    private void ballBrickCollision(){
        // Check for ball colliding with a brick
        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].getVisibility()) {
                if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                    bricks[i].setInvisible();
                    if(!debris[i].getDebrisType().equals("None")) {
                        debris[i].activate();
                    }
                    ball.reverseYVelocity();
                    score = score + 10;
                }
            }
        }
    }

    private void ballPaddleCollision(){
        // Check for ball colliding with paddle
        if(ball.intersect(bat)) {

            // Interpolate the incoming position for computation of the new Velocity
            float midBall = ball.getMiddle();
            float midBat = bat.getMiddle();
            float fracDisplacementFromMid = (midBall - midBat) / midBat;

            ball.getNewVelocity(fracDisplacementFromMid, bat);

        }
    }

    private boolean checkMissBall(){
        if (ball.getRect().bottom > screenY) {
            // Lose a life
            lives--;
            ball.reset(screenX, screenY, this.level);
            paused = true;

            if (lives == 0) {
                paused = true;

                //draw Loss;
                canvas = ourHolder.lockCanvas();
                canvas.drawText("Game Over!",
                        screenX / 2 - (densityDpi / 1.90f), screenY / 2 + (densityDpi), paint);
                ourHolder.unlockCanvasAndPost(canvas);

                try {
                    // Wait 3 seconds then reset a new game
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Create bricks at level 1
                createBricksAndRestart(1);
            }
            return true;
        }
        return false;
    }

    private void checkWallBounce(){
        // Bounce the ball back when it hits the top of screen
        if (ball.getRect().top < 0) {
            ball.reverseYVelocity();
            ball.clearObstacleY(40);
        }

        // If the ball hits left wall bounce
        if (ball.getRect().left < 0) {
            ball.reverseXVelocity();
            ball.clearObstacleX(2);
        }

        // If the ball hits right wall Velocity
        if (ball.getRect().right > screenX) {
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 57);
        }
    }

    private void succeedToLevelTwo(){
        // Create bricks at level 2
        createBricksAndRestart(2);

        // fix for a pause bug
        // so that it won't Pause After finishing the Game
        score = score + 10;
        // Gift the player with 1 new live
        lives = lives + 1;
    }

    private void succeedToLevelThree(){
        // Create bricks at level 3
        createBricksAndRestart(3);

        // fix for a pause bug
        // so that it won't Pause After finishing the Game
        score = score + 10;
        // Gift the player with 2 new lives
        lives = lives + 2;
    }

}
