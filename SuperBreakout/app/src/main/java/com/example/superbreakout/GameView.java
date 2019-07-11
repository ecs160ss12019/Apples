package com.example.superbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

class GameView extends SurfaceView implements Runnable{

    /*
     * All game objects have been commented before they have been implemented.
     * Uncomment objects after they have been implemented.
     * */

    // Are we debugging?
    private final boolean DEBUGGING = true;

    /* These objects are needed to do the drawing
     */
    private SurfaceHolder holder; // Our frame of the view
    private Canvas canvas; // The canvas that we draw on
    private Paint paint; // A paint object for drawing

    /* These variables handle the rate at which the veiw refreshes
     */
    private long mFPS; // Frame rate of the game (rate at which the view refreshes)
    private final int MILLIS_IN_SECOND = 1000; // The number of milliseconds in a second

    /* These variables hold the resolution of the screen
     */
    private int width; // Horizontal resolution
    private int height; // Vertical resolution

    /* These variables hold properties of the font
     */
    private int fontSize; // Size of words we print on the screen
    private int fontMargin; // Margin between words we print on the screen

    /* The game objects
     */
    // private Bat bat; // This is used to reflect
    private Ball[] balls; // This ball array stores all the balls that the user has
    // private Obstacle[] bricks; // These are the bricks that the player is supposed to break
    // private Debris[] debris; // These are the upgrades and downgrades that are generated by the broken bricks

    /* This counter determines how many balls the player has left
     */
    private int numBalls; // This should be equals to balls.length
    private int level; // This indicates the level of difficulty

    /* Here is the Thread that the game is running on
     * and two control variables
     */
    private Thread gameThread = null;
    // This volatile variable can be accessed
    // from inside and outside the thread
    private volatile boolean playing;
    private boolean paused = true;

    /* The Game constructor
     * Called when this line:
     * SuperBreakoutGame = new GameView(this, size.x, size.y);
     * is executed from SuperBreakoutActivity
     *
     * @context: The context of the activity
     * @x: horizontal screen size
     * @y: vertical screen size
     */
    public GameView(Context context, int x, int y) {

        super(context); // Calls the parent class, constructor of SurfaceView

        /* Initialize horizontal resolution to @x
         * Initialize vertical resolution to @y
         */
        width = x;
        height = y;

        /* Initialize font properties
         */
        fontSize = width / 20;
        fontMargin = width / 75;

        /* Initialize the objects that we draw with
         */
        holder = getHolder();
        paint = new Paint();

        /* Initialize the bat and ball
         */
        balls = new Ball[3];
        balls[0] = new Ball(width);
        //bat = new Bat(width, height);

        startNewGame(level);

    }

    public void handleCollisions() {
        // how do we handle collision
    }

    @Override
    public void run() {
        while(playing){
            draw();
        }
        // run implementation
    }

    /*
     * Draws the game's layout (frame) and objects
     * */
    public void draw() {
        if (holder.getSurface().isValid()) {
            // Lock the canvas (graphics memory) ready to draw
            canvas = holder.lockCanvas();

            // Fill the screen with a solid color
            canvas.drawColor(Color.argb
                    (255, 153, 204, 255));

            // Choose a color to paint with
            paint.setColor(Color.argb
                    (255, 255, 255, 255));

            canvas.drawRect(balls[0].getRect(), paint);


            // Choose the font size
            paint.setTextSize(fontSize);
            paint.setColor(Color.argb(255,255,255,255));

            // Draw the HUD
            canvas.drawText("Number of Balls: " + numBalls, fontMargin, fontSize + 20, paint);
            canvas.drawText("Level: " + level, fontMargin, fontSize + 150, paint);
            // Display the drawing on screen
            // unlockCanvasAndPost is a method of SurfaceView
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /* This function is called to start a new game
     * according to a level
     */
    private void startNewGame(int level){

        switch (level) {
            case 1:
                // set up tiles
                break;
            case 2:
                // set up tiles
                break;
            case 3:
                // set up tiles
                break;
        }

        // Sets up random number generator to spawn ball at random locations in the game
        Random r = new Random();
        int rWidth = r.nextInt(width);

        balls[0].placeBall(rWidth, height); // place the ball

    }

    /* Method called by SuperBreakoutActivity to pause game
     */
    public void pause() {

        playing = false;
        try {
            // Stop the thread
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
        // Show menu?

    }


    /* Method called by SuperBreakoutActivity to resume game
     */
    public void resume() {

        playing = true;
        gameThread = new Thread(this);  // Initialize the instance of Thread
        gameThread.start(); // Start the thread
    }
}
