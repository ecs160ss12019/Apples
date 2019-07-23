package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

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
    Player player;
    Level level;

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
        level = new LevelOne(screenX, screenY);
        startNewGame();

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
        //updateDebris(fps);
        //ballDebrisCollision();
        //batDebrisCollision();

        ball.checkBallBatCollision(bat);

        if(!checkMissBall()) {
           if(level.checkCollision(ball)){
                // Add points to Player
                if(level.levelCompleted()){
                    level = level.advanceNextLevel();
                    level.createBricks(getContext());
                }
            }
            ball.checkWallBounce();
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

            drawBall();
            drawBat();
            drawStage();
            drawStats();
            checkAndDrawWinScreen();

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
                if (player.isAlive()){ paused = false;}
                bat.move(motionEvent.getX());
                break;

            case MotionEvent.ACTION_UP: // Player doesn't touch screen
                bat.stopMoving();
                break;
        }
        return true;
    }

    /************ HELPER FUNCTIONS ************/
    private void startNewGame(){
        player = new Player();
        level.createBricks(getContext());
        ball.reset(screenX, screenY, level.getLevel());
        bat.reset(level.getLevel());
    }

    private void endGame(){
        //draw Loss;
        canvas = ourHolder.lockCanvas();
        canvas.drawText("Game Over!",
                screenX / 2 - (densityDpi / 1.90f), screenY / 2 + (densityDpi), paint);
        ourHolder.unlockCanvasAndPost(canvas);
        startNewGame();

        try {
            // Wait 3 seconds then reset a new game
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean checkMissBall(){
        if (ball.checkMissBall()) {
            // Lose a life
            player.reduceLifeByOne();
            ball.reset(screenX, screenY, level.getLevel());
            paused = true;
            if (!player.isAlive()) {
                endGame();
                // Create bricks at level 1
            }
            return true;
        }
        return false;
    }

    private void drawStats(){
        // Choose the brush color for drawing
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(50);

        // Score Text
        canvas.drawText(
                "Score: " + player.getScore()
                , densityDpi / 5, (screenY / 2) + (densityDpi / 1.50f), paint);

        // Lives Text
        canvas.drawText("Lives: " + player.getLives()
                , densityDpi / 5, screenY / 2, paint);

        // Levels Text
        canvas.drawText("Level: " + level.getLevel()
                , densityDpi / 5, screenY / 2 + (densityDpi / 3f), paint);

    }

    private void checkAndDrawWinScreen(){
        if (level.getLevel() == LevelThree.LEVEL_THREE && level.levelCompleted()) {
            paint.setColor(getResources().getColor(R.color.colorAccent));
            canvas.drawText("You got home!", screenX / 2 - (densityDpi / 1.90f), screenY / 2 +
                    (densityDpi / 1), paint);
        }

    }

    private void drawStage(){
        // Draw stage
        paint.setColor(Color.argb(255, 255, 0, 0));
        level.draw(canvas,paint);
    }

    private void drawBat(){
        canvas.drawBitmap(bat.getBatBitmap(), bat.getRect().left, bat.getRect().top, null);
    }

    private void drawBall(){
        canvas.drawBitmap(ball.getBallBitmap(),ball.getRect().left,ball.getRect().top,paint);
    }

    /*
    private void updateDebris(long fps) {
        // Updates the position of all active debris
        for (int i = 0; i < numBricks; i++) {
            if(debris[i].getActive()) {
                debris[i].update(fps);
            }
        }
    }

    private void ballDebrisCollision() {
        // Check for ball colliding with a debris
        for(int i = 0; i < numBricks; i++) {
            if(debris[i].getActive()) {
                if(RectF.intersects(debris[i].getRect(), ball.getRect())) {
                    debris[i].deactivate();
                }
            }
        }
    }

    private void batDebrisCollision() {
        // Check for bat colliding with a Debris
        for(int i = 0; i < numBricks; i++) {
            if(debris[i].getActive()) {
                if(RectF.intersects(debris[i].getRect(), bat.getRect())) {
                    debris[i].deactivate();
                    // implement apply debris effect to bat
                }
            }
        }
    }
    */
}
