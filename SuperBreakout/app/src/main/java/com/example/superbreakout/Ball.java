package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.util.Random;

public class Ball {
    private RectF rect;
    public float xVelocity;
    public float yVelocity;

    // Make it a 10 pixel x 10 pixel square
    float ballWidth = 10;
    float ballHeight = 10;

    private Bitmap ballBitmap;

    public Ball(Context context, int screenX, int screenY) {
        // creates new rectangle object for ball
        rect = new RectF();

        // loads in asset and turns it into bitmaps
        ballBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        ballBitmap = Bitmap.createScaledBitmap(ballBitmap, 75, 65, true);
    }

    public RectF getRect() {
        return rect;
    }

    public Bitmap getBallBitmap() { return ballBitmap; }

    public void update(long fps) {
        rect.left = rect.left + (xVelocity / fps);
        rect.top = rect.top + (yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;
    }

    public void reverseYVelocity() {
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity() {
        xVelocity = -xVelocity + 50;
    }

    public void sameXVelocity() {
        xVelocity = xVelocity + 50;
    }

    public void zeroXVelocity() {
        xVelocity = xVelocity - 50;
    }

    public void setRandomXVelocity() {
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if (answer == 0) {
            reverseXVelocity();
        }
    }

    // a fix for bug in Android RectF Class
    public void clearObstacleY(float y) {
        rect.bottom = y;
        rect.top = y - ballHeight;
    }

    // a fix for bug in Android RectF Class
    public void clearObstacleX(float x) {
        rect.left = x;
        rect.right = x + ballWidth + 50;
    }

    public void reset(int x, int y) {

        // Place the ball in the centre of the screen at the bottom
        rect.left = x / 2;
        rect.top = y - 200;
        rect.right = x / 2 + ballWidth;
        rect.bottom = y - 100 - ballHeight;

        // Start the ball travelling straight up at 400 pixels per second
        xVelocity = 400;
        yVelocity = -800;


    }


}