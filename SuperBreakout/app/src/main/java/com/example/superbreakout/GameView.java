package com.example.superbreakout;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import com.example.superbreakout.Level;
import com.example.superbreakout.Level.*;

import androidx.core.view.GestureDetectorCompat;

import java.io.IOException;
import java.lang.Math;

public class GameView extends SurfaceView implements Runnable {

    // OS stuff
    Thread gameThread = null;
    SurfaceHolder ourHolder;
    Canvas canvas;
    Paint paint;
    long fps; // sets the frame rate for the game

    volatile boolean playing;
    boolean paused = true;

    // Resolution of screen
    int screenX;
    int screenY;

    // Objects of the game
    Bat bat;
    Ball ball;
    Player player;
    Level level;

    // Screen items
    Rect dest;
    DisplayMetrics dm;
    int densityDpi;

    Randomizer randomizer;

    // Sounds
    // SoundPool sp;
    // SoundEffects FX;

    // Sets gesture compat object
    private GestureDetectorCompat gestureDetectorCompat = null;


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
        startNewGame();

        randomizer = new Randomizer();
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
                fps = 10000 / timeThisFrame;
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
                player.hitBrick();
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
            // Gets current context
            Resources res = getContext().getResources();

            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            // canvas.drawColor(Color.argb(255, 153, 204, 255));

            // Gets resources for background images
            Bitmap backgroundImage = BitmapFactory.decodeResource(res, R.drawable.hills_layer_1);
            Bitmap clouds = BitmapFactory.decodeResource(res, R.drawable.clouds);


            // Gets background dimensions
            dest = new Rect(0, 0, getWidth(), getHeight());

            // Draws background image
            canvas.drawBitmap(backgroundImage, null, dest, paint);
            // paint.setAlpha(100);
            // canvas.drawBitmap(clouds, null, dest, paint);
            // paint.setAlpha(255);


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

            case MotionEvent.ACTION_MOVE:
                if (!(player.getLives() == 0)){ paused = false;}
                bat.move(motionEvent.getX());
                break;
        }

        return true;

    }

    /************ HELPER FUNCTIONS ************/
    private void startNewGame(){
        player = new Player();
        //TODO change starting levels here
        level = new LevelOne(screenX, screenY, getContext());
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

        try {
            // Wait 3 seconds then reset a new game
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startNewGame();

    }

    private boolean checkMissBall(){
        if (ball.checkMissBall()) {
            player.missBrick(); // Reset points
            player.reduceLifeByOne(); // Lose a life


            ball.reset(screenX, screenY, level.getLevel());
            paused = true;

            if (!player.isAlive()) {
                endGame();
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
