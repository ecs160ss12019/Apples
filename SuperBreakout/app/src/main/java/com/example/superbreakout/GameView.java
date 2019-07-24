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

    Bitmap backgroundImage;

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

        Resources res = getContext().getResources();


        backgroundImage = BitmapFactory.decodeResource(res, R.drawable.hills_layer_1);
        canvas = new Canvas();

        screenX = x;
        screenY = y;

        dm = context.getResources().getDisplayMetrics();
        densityDpi = dm.densityDpi;

        bat = new Bat(context, screenX, screenY, densityDpi);
        ball = new Ball(context, screenX, screenY);
        startNewGame();

        randomizer = new Randomizer();
        // FX = new SoundEffects(getContext());


        // Instantiate a SoundPool dependent on Android version
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // The new way
            // Build an AudioAttributes object
            AudioAttributes audioAttributes =
                    // First method call
                    new AudioAttributes.Builder()
                            // Second method call
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            // Third method call
                            .setContentType
                                    (AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            // Fourth method call
                            .build();

            // Initialize the SoundPool
            sp = new SoundPool.Builder()
                    .setMaxStreams(3) // sets maximum amount of fx at a single instance to be 3
                    .setAudioAttributes(audioAttributes)
                    .build();
        } */

        /*
        try{
            // Create objects of the 2 required classes
            AssetManager assetManager = getContext().getAssets();
            AssetFileDescriptor descriptor1;
            AssetFileDescriptor descriptor2;
            // Load our fx in memory ready for use
            descriptor1 = assetManager.openFd("blip-1.wav");
            idFX1 = sp.load(descriptor1, 0);
        } catch(IOException e){
            // Print an error message to the console
            Log.d("Error", "=Failed to load sound files");
        } */


        // Create bricks for level 1
        // createBricksAndRestart(1);
    }


    /*
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

                if(randomizer.getRandBoolean() && level > 1){
                    bricks[numBricks].setDurability(randomizer.getRandNumber(1,3));
                }
                else {
                    bricks[numBricks].setDurability(0);
                }


                // can possibly change this to spawnDebris()
                debris[numBricks] = new Debris(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }

        // if Game is over reset scores ,lives &Level
        if (lives == 0) { restartGame();}

    }*/



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
            // Bitmap backgroundImage = BitmapFactory.decodeResource(res, R.drawable.hills_layer_1);
            // Bitmap clouds = BitmapFactory.decodeResource(res, R.drawable.clouds);


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
        canvas.drawBitmap(bat.getBatBitmap(), null, bat.getRect(), paint);
    }

    private void drawBall(){
        canvas.drawBitmap(ball.getBallBitmap(),ball.getRect().left, ball.getRect().top, paint);
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
