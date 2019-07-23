package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Bat extends GameObject {

    enum Direction{
        STOPPED,
        LEFT,
        RIGHT
    }

    int scrX;
    // RectF is an object that holds four coordinates - just what we need
    private RectF rect;

    // X is the far left of the rectangle which forms our paddle
    private float x;
    // Y is the top coordinate
    private float y;
    // This will hold the pixels per second speed that the paddle will move
    private float paddleSpeed;

    private Direction paddleMoving = Direction.STOPPED;
    private int MYscreenDPI;

    private Bitmap batBitmap;
    private BitmapDimensions bitmapDimensions; // specifies the dimensions of the bitmap image


    // This the the constructor method
    // When we create an object from this class we will pass
    // in the screen width and height
    public Bat(Context context, int screenX, int screenY, int screenDPI) {
        super(screenDPI/ 2, screenDPI/5);

        bitmapDimensions = new BitmapDimensions((int)width, (int)height);

        MYscreenDPI = screenDPI;
        scrX = screenX;

        // Start paddle in roughly the sceen centre
        x = screenX / 2;
        y = screenY - screenDPI / 4.50f;

        rect = new RectF(x, y, x + width, y + height);

        batBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bat);
        batBitmap = Bitmap.createScaledBitmap(batBitmap,
                bitmapDimensions.width,
                bitmapDimensions.height,
                true);
    }

    public void reset(int Xlevel) {
        switch (Xlevel) {
            case 1:
                setPaddleSpeed(8000);
                break;
            case 2:
                setPaddleSpeed(9000);
                break;
            case 3:
                setPaddleSpeed(10000);
                break;
            case 4:
                setPaddleSpeed(11000);
                break;
            default:
                setPaddleSpeed(12000);
                break;
        }
    }

    // This update method will be called from update in BreakoutView
    // It determines if the paddle needs to move and changes the coordinates
    // contained in rect if necessary
    public void update(long fps) {

        if (paddleMoving == Direction.LEFT && x >= -MYscreenDPI/10){
            x -= paddleSpeed / fps;
        }else if (paddleMoving == Direction.RIGHT && x <= scrX - width - MYscreenDPI/14){
            x += paddleSpeed/fps;
        }

        // Apply the New position
        rect.left = x;
        rect.right = x + width;
    }


    public boolean checkMovementStateLeft(){
        if (paddleMoving == Direction.LEFT) return true;
        else return false;
    }

    public boolean checkMovementStateRight(){
        if (paddleMoving == Direction.RIGHT) return true;
        else return false;
    }

    public void move(float direction ){
        if (direction > scrX / 2) { // Right half move right
            paddleMoving = Direction.RIGHT;
        }else{ // Else move left
            paddleMoving = Direction.LEFT;
        }
    }

    public float getMiddle() {
        return (getRect().right - getRect().left) / 2;
    }

    public RectF getRect() {
        return rect;
    }

    public Bitmap getBatBitmap() {return batBitmap;}

    public float getPaddleSpeed(){ return paddleSpeed;}

    public void stopMoving(){ paddleMoving = Direction.STOPPED;}

    // Setter for paddleSpeed
    // Might need these if there are ugprades or downgrades affecting paddle speed
    public void setPaddleSpeed(float speed) {
        this.paddleSpeed = speed;
    }
}
