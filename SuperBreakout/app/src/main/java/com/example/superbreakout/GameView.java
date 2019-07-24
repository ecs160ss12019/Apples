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

        ballBrickCollision();
        ballPaddleCollision();
        debrisCollision();

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
            canvas.drawColor(Color.argb(255, 153, 204, 255));

            dest = new Rect(0, 0, getWidth(), getHeight());

            // Choose the brush color for drawing white
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the ball
            // canvas.drawCircle(ball.getRect().centerX(), ball.getRect().centerY(), 25, paint);
            canvas.drawBitmap(ball.getBallBitmap(),ball.getRect().left,ball.getRect().top,paint);

            if(bat.stunTimer > 0) {
                // Sets brush color to black if the bat is stunned
                paint.setColor(Color.argb(255, 0, 0, 0));
            } else {
                // Red otherwise
                paint.setColor(Color.argb(255, 255, 0, 0));
            }

            // Draw the paddle
            canvas.drawRect(bat.getRect(), paint);
//            canvas.drawBitmap(bat.getBatBitmap(), bat.getRect().left, bat.getRect().top, null);


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

    private void debrisCollision() {
        // Check for ball or bat colliding with a debris
        for(int i = 0; i < numBricks; i++) {
            if(debris[i].getActive()) {
                /*
                 hit-box of collision kinda confusing since sometimes the ball will go through it,
                 and debris will not disappear
                  */
                if(RectF.intersects(debris[i].getRect(), ball.getRect())) {
                    // Check if there is ball/debris collision
                    debris[i].deactivate();
                } else if(RectF.intersects(debris[i].getRect(), bat.getRect())) {
                    // check if there is a bat/debris collision

                    // receive effect
                    switch(debris[i].getDebrisType()) {
                        case "Harmful":
                            // Stun bat for a few secs
                            bat.stun();
                            break;
                        case "Upgrade":
                            applyUpgrade(ug[i]);
                            break;
                        case "Downgrade":
                            applyDowngrade(dg[i]);
                            break;
                    }

                    debris[i].deactivate();
                }
            }
        }
    }

    private void applyUpgrade(Upgrade ug) {
        switch(ug.getEffectTarget()) {
            case "Ball":
                ball.applyUpgrade(ug.upgradeName);
                break;
            case "Bat":
                bat.applyUpgrade(ug.upgradeName);
                break;
        }
    }

    private void applyDowngrade(Downgrade dg) {
        switch(dg.getEffectTarget()) {
            case "Ball":
                ball.applyDowngrade(dg.downgradeName);
                break;
            case "Bat":
                bat.applyDowngrade(dg.downgradeName);
                break;
        }
    }


    private void ballBrickCollision(){
        // Check for ball colliding with a brick
        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].getVisibility()) {
                if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                    bricks[i].setInvisible();

                    // If ball has explosion upgrade
                    /*
                    if(ball.Explosion) {
                        if() {
                            // Conditions for top row
                        } else if() {
                            // Conditions for bottom row
                        } else {
                            // Conditions for rows in between.
                        }
                    }
                    */

                    if(!debris[i].getDebrisType().equals("None")) {
                        debris[i].activate();

                        // Storing the effect based on the debris type.
                        if(debris[i].getDebrisType().equals("Upgrade")) {
                            ug[i] = new Upgrade();
                        } else if(debris[i].getDebrisType().equals("Downgrade")) {
                            dg[i] = new Downgrade();
                        }
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
