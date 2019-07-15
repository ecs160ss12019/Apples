package com.example.superbreakout;

import android.graphics.RectF;

public class Bat {

    // Which ways can the paddle move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    int scrX;
    private RectF rect;

    private float length;

    private float x;
    private float speed;

    private int direction = STOPPED;
    private int MYscreenDPI;

    public Bat(int screenX, int screenY, int screenDPI) {
        // Dynamic size based on each device DPI
        length = screenDPI / 2;
        float height = screenDPI / 5;
        MYscreenDPI = screenDPI;
        scrX = screenX;
        x = screenX / 2;
        float y = screenY - screenDPI / 4.50f;

        rect = new RectF(x, y, x + length, y + height);

        speed = 800;
    }


    public RectF getRect() { return rect;}

    public int getMovementState() { return direction;}
    public void moveLeft(){ direction = LEFT;}
    public void moveRight(){ direction = RIGHT;}
    public void moveStop(){ direction = STOPPED;}


    // This update method will be called from update in BreakoutView
    // It determines if the paddle needs to move and changes the coordinates
    // contained in rect if necessary
    public void update(long fps) {
        if (direction == LEFT) {
            // to fix Paddle going off the Screen
            if (x >= -MYscreenDPI / 10)
                // Decrement position
                x = x - speed / fps;
        }

        if (direction == RIGHT) {
            // to fix Paddle going off the Screen
            if (x <= scrX - length - MYscreenDPI / 14)
                // Increment position
                x = x + speed / fps;
        }

        // Apply the New position
        rect.left = x;
        rect.right = x + length;
    }

}