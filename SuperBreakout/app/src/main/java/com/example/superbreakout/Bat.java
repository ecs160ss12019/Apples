package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Bat extends GameObject {

    // Which ways can the paddle move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    int scrX;
    // RectF is an object that holds four coordinates - just what we need
    private RectF rect;

    // X is the far left of the rectangle which forms our paddle
    private float x;
    // Y is the top coordinate
    private float y;
    // This will hold the pixels per second speedthat the paddle will move
    public float paddleSpeed;
    // Is the paddle moving and in which direction
    private int paddleMoving = STOPPED;
    private int MYscreenDPI;

    private Bitmap batBitmap;
    private BitmapDimensions bitmapDimensions; // specifies the dimensions of the bitmap image


    // This the the constructor method
    // When we create an object from this class we will pass
    // in the screen width and height
    public Bat(Context context, int screenX, int screenY, int screenDPI) {
        super(screenDPI/ 2, screenDPI/5);
        // Dynamic size based on each device DPI
        // length = screenDPI / 2;
        // height = screenDPI / 5;

        bitmapDimensions = new BitmapDimensions((int)width, (int)height);

        MYscreenDPI = screenDPI;
        scrX = screenX;

        // Start paddle in roughly the sceen centre
        x = screenX / 2;
        y = screenY - screenDPI / 4.50f;

        rect = new RectF(x, y, x + width, y + height);

        // How fast is the paddle in pixels per second
        paddleSpeed = 800;

        batBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bat);
        batBitmap = Bitmap.createScaledBitmap(batBitmap, bitmapDimensions.width, bitmapDimensions.height, true);
    }

    // This is a getter method to make the rectangle that
    // defines our paddle available in BreakoutView class
    public RectF getRect() {
        return rect;
    }

    public Bitmap getBatBitmap() {return batBitmap;}

    public int getMovementState() {
        return paddleMoving;
    }

    public void moveRight(){ paddleMoving = RIGHT;}
    public void moveLeft(){ paddleMoving = LEFT;}
    public void moveStop(){ paddleMoving = STOPPED;}

    // This update method will be called from update in BreakoutView
    // It determines if the paddle needs to move and changes the coordinates
    // contained in rect if necessary
    public void update(long fps) {

        if (paddleMoving == LEFT && x >= -MYscreenDPI/10){
            x -= paddleSpeed / fps;
        }else if (paddleMoving == RIGHT && x <= scrX - width - MYscreenDPI/14){
            x += paddleSpeed/fps;
        }

        // Apply the New position
        rect.left = x;
        rect.right = x + width;
    }

    public float getMiddle() {
        return (getRect().right - getRect().left) / 2;
    }

}