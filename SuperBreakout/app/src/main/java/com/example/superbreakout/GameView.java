package com.example.superbreakout;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
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

    int screenX;
    int screenY;

    Bat bat;
    Ball ball;
    Obstacle[] bricks = new Obstacle[24];
    int numBricks = 0;

    int score = 0;
    int level = 1;
    int lives = 3;

    Rect dest;
    DisplayMetrics dm;
    int densityDpi;

    public GameView(Context context, int x, int y) {
        super(context);

        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        dm = context.getResources().getDisplayMetrics();
        densityDpi = dm.densityDpi;

        bat = new Bat(screenX, screenY, densityDpi);
        ball = new Ball(screenX, screenY);

        // Create bricks for level 1
        createBricksAndRestart(1);

    }


    public void createBricksAndRestart(int Xlevel) {

        // Put the ball back to the start
        ball.reset(screenX, screenY);

        level = Xlevel;
        switch (Xlevel) {

            // level 1
            default:
                ball.xVelocity = 400;
                ball.yVelocity = -800;
                break;

        }

        // Brick Size
        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;

        // Build a wall of bricks
        numBricks = 0;
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 3; row++) {
                bricks[numBricks] = new Obstacle(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }

        // if Game is over reset scores ,lives &Level
        if (lives == 0) {
            score = 0;
            lives = 3;
            level = 1;
        }

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

        // Check for ball colliding with a brick
        for (int i = 0; i < numBricks; i++) {

            if (bricks[i].getVisibility()) {

                if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                    bricks[i].setInvisible();
                    ball.reverseYVelocity();
                    score = score + 10;

                    //soundPool.play(explodeID, 1, 1, 0, 0, 1); FIXME
                }
            }
        }

        // Check for ball colliding with paddle
        if(ball.intersect(bat)) {

            // Interpolate the incoming position for computation of the new Velocity
            float midBall = ball.getMiddle();
            float midBat = bat.getMiddle();
            float fracDisplacementFromMid = (midBall - midBat) / midBat;

            ball.getNewVelocity(fracDisplacementFromMid, bat);

        }

        if (ball.getRect().bottom > screenY) {

            // Lose a life
            lives--;
            ball.reset(screenX, screenY);
            paused = true;

            if (lives == 0) {
                paused = true;


                //draw Loss;
                canvas = ourHolder.lockCanvas();
                //paint.setColor(getResources().getColor(R.color.orange));
                //paint.setTextSize(getResources().getDimension(R.dimen.text_size_big));
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

        }
        // Pause if cleared screen
        if (score == numBricks * 10) {

            // Create bricks at level 2
            createBricksAndRestart(2);

            // fix for a pause bug
            // so that it won't Pause After finishing the Game
            score = score + 10;
            // Gift the player with 1 new live
            lives = lives + 1;

        } else if (score == (numBricks * 20) + 10) {

            // Create bricks at level 3
            createBricksAndRestart(3);

            // fix for a pause bug
            // so that it won't Pause After finishing the Game
            score = score + 10;
            // Gift the player with 2 new lives
            lives = lives + 2;

        }
        // Pause if cleared screen
        // if score equals to the whole Bricks scores after 3 levels
        else if (score == (numBricks * 10 * 3) + 20) {
            paused = true;
        }


        // Bounce the ball back when it hits the top of screen
        if (ball.getRect().top < 0) {
            ball.reverseYVelocity();
            ball.clearObstacleY(40);

            //soundPool.play(beep2ID, 1, 1, 0, 0, 1);
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

    // Draw the newly updated scene
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255, 153, 204, 255));
            // canvas.drawColor(getResources().getColor(R.color.deeppurple));

            dest = new Rect(0, 0, getWidth(), getHeight());
            // Draw bob as background with dest size
            //canvas.drawBitmap(bitmapBob, null, dest, paint);

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the ball
            canvas.drawCircle(ball.getRect().centerX(), ball.getRect().centerY(), 25, paint);
            //canvas.drawBitmap(bitmapBall, ball.getRect().left, ball.getRect().top, null);

            // Draw the paddle
            canvas.drawRect(bat.getRect(), paint);
            //canvas.drawBitmap(bitmapPaddal, paddle.getRect().left, paddle.getRect().top, null);


            // Change the brush color for drawing
            // paint.setColor(getResources().getColor(R.color.redorange));

            // Draw the bricks if visible
            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getVisibility())
                    canvas.drawRect(bricks[i].getRect(), paint);
            }

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));
            // Draw the score
            // paint.setTextSize(getResources().getDimension(R.dimen.text_size));
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
                //paint.setTextSize(getResources().getDimension(R.dimen.text_size_big));
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

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                if (!(lives == 0)) {

                    paused = false;
                }

                // If touch motion > Half of the Screen
                if (motionEvent.getX() > screenX / 2) {

                    // move paddle right
                    bat.setMovementState(bat.RIGHT);

                } else {

                    // move paddle left
                    bat.setMovementState(bat.LEFT);
                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                // paddle stopped
                bat.setMovementState(bat.STOPPED);
                break;
        }
        return true;
    }
}