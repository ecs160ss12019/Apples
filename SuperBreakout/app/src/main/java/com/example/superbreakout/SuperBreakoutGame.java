package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class SuperBreakoutGame extends SurfaceView implements Runnable{

    // Are we debugging?
    private final boolean DEBUGGING = true;

    // These objects are needed to do the drawing
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;

    // How many frames per second did we get?
    private long mFPS;
    // The number of milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;

    // Holds the resolution of the screen
    private int mScreenX;
    private int mScreenY;
    // How big will the text be?
    private int mFontSize;
    private int mFontMargin;

    // The game objects
    private Bat mBat;
    private Ball mBall;

    // The current score and lives remaining
    private int numBalls = 3;

    // Here is the Thread and two control variables
    private Thread mGameThread = null;
    // This volatile variable can be accessed
    // from inside and outside the thread
    private volatile boolean mPlaying;
    private boolean mPaused = true;

    // The PongGame constructor
    // Called when this line:
    // mPongGame = new PongGame(this, size.x, size.y);
    // is executed from PongActivity
    public SuperBreakoutGame(Context context, int x, int y) {
        // Super... calls the parent class
        // constructor of SurfaceView
        // provided by Android
        super(context);

        // Initialize these two members/fields
        // With the values passes in as parameters
        mScreenX = x;
        mScreenY = y;

        // Font is 5% (1/20th) of screen width
        mFontSize = mScreenX / 20;
        // Margin is 1.5% (1/75th) of screen width
        mFontMargin = mScreenX / 75;

        // Initialize the objects
        // ready for drawing with
        // getHolder is a method of SurfaceView
        mOurHolder = getHolder();
        mPaint = new Paint();

        // Initialize the bat and ball
        //mBall = new Ball(mScreenX);
        //mBat = new Bat(mScreenX, mScreenY);

        // Everything is ready so start the game
        startNewGame();
    }

    @Override
    public void run() {
        // run implementation
    }

    // The player has just lost
    // or is starting their first game
    private void startNewGame(){

        // Put the ball back to the starting position
        //mBall.reset(mScreenX, mScreenY);

        // Reset the score and the player's chances
        numBalls = 3;

    }

    // This method is called by PongActivity
    // when the player quits the game
    public void pause() {

        // Set mPlaying to false
        // Stopping the thread isn't
        // always instant
        mPlaying = false;
        try {
            // Stop the thread
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }


    // This method is called by PongActivity
    // when the player starts the game
    public void resume() {
        mPlaying = true;
        // Initialize the instance of Thread
        mGameThread = new Thread(this);

        // Start the thread
        mGameThread.start();
    }
}
