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

    public int stunTimer; // indicates the amount of time the bat is stunned for when hit by a debris

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

        // How fast is the paddle in pixels per second
        paddleSpeed = 800;

        batBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bat);
        batBitmap = Bitmap.createScaledBitmap(batBitmap,
                bitmapDimensions.width,
                bitmapDimensions.height,
                true);
    }

    // This update method will be called from update in BreakoutView
    // It determines if the paddle needs to move and changes the coordinates
    // contained in rect if necessary
    public void update(long fps) {

        // Do not update (move), if the bat is stunned.
        if(stunTimer == 0) {
            if (paddleMoving == Direction.LEFT && x >= -MYscreenDPI/10){
                x -= paddleSpeed / fps;
            }else if (paddleMoving == Direction.RIGHT && x <= scrX - width - MYscreenDPI/14){
                x += paddleSpeed/fps;
            }

            // Apply the New position
            rect.left = x;
            rect.right = x + width;
        } else {
            stunTimer -= 1;
        }
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

    public void applyUpgrade(String upgradeName) {
        /*
         * Add remaining upgrade cases
         * implement a way to store list of upgrades.
         * List of Effects should reset after each death/level
         */
        switch(upgradeName) {
            case "SpeedUp":
                paddleSpeed = 2 * paddleSpeed;
                break;
            case "SizeUp":
                // change size
                // BUG: Reverts back to normal size after 1s
                float width = rect.width()/4;
                rect.left = rect.left - width;
                rect.right = rect.right + width;
                break;
        }
    }

    public void stun() {
        // adds to the stunTimer

        // If the brick is already stunned, do not add. If not, then add to the timer. (avoids stacking)
        if(stunTimer == 0) {
            stunTimer += 20;
        }
    }

}
