package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import java.util.Random;

public class Ball {

    private RectF rect;
    public double xVelocity;
    public double yVelocity;
    public double ballSpeed;

    // Make it a 10 pixel x 10 pixel square
    float ballWidth = 10;
    float ballHeight = 10;

    private Bitmap ballBitmap;

    public Ball(Context context, int level) {

        // creates new rectangle object for ball
        rect = new RectF();

        // loads in asset and turns it into bitmaps
        ballBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        ballBitmap = Bitmap.createScaledBitmap(ballBitmap, 75, 65, true);

        switch (level) {
            case 1:
                ballSpeed = 800;
                break;

            case 2:
                ballSpeed = 1000;
                break;

            case 3:
                ballSpeed = 1200;
                break;
        }

    }

    public void reset(int x, int y, int level) {

        // Place the ball in the centre of the screen at the bottom
        rect.left = x / 2;
        rect.top = y - 200;
        rect.right = x / 2 + ballWidth;
        rect.bottom = y - 100 - ballHeight;

        // Start the ball travelling straight up at 400 pixels per second
        this.setRandomVelocity();

    }

    public RectF getRect() { return rect; }

    public Bitmap getBallBitmap() { return ballBitmap; }

    public void update(long fps) {

        rect.left = rect.left + ((float)xVelocity / fps);
        rect.top = rect.top + ((float)yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;

    }

    public void reverseYVelocity() { yVelocity = -yVelocity; }

    public void reverseXVelocity() { xVelocity = -xVelocity; }

    public int boundedRandomInt(Random random, int high, int low) { return random.nextInt(high - low) + low; }

    public void setRandomVelocity() {

        Random generator = new Random();
        int VxRatio = boundedRandomInt(generator, 4, 8);
        int VyRatio = boundedRandomInt(generator,  16, 10);

        normalizeVelocity(VxRatio, VyRatio);

    }

    public void normalizeVelocity(double xRatio, double yRatio) {

        double compensationFactor = this.ballSpeed / Math.sqrt(xRatio*xRatio + yRatio*yRatio);
        this.xVelocity = compensationFactor * xRatio;
        this.yVelocity = -compensationFactor * yRatio; // Negative because we want to generate an upwards velocity

    }

    public float getMiddle() { return (this.getRect().right - this.getRect().left) / 2; }

    public boolean intersect(Bat bat) {

        if (this.getRect().intersect(bat.getRect())) {
            return true;
        }
        if(RectF.intersects(bat.getRect(), this.getRect())) {
            return true;
        }
        if(bat.getRect().intersect(this.getRect())) {
            return true;
        }

        return false;
    }

    public void setNewVelocity(float fraction, Bat bat) {

        double newY = this.yVelocity + fraction * this.yVelocity/2; // negative sign is set in normalize
        double newX = this.xVelocity - fraction * this.xVelocity/2;

        if (bat.getMovementState() == bat.RIGHT) {
            newX += bat.paddleSpeed/10;
        }
        else if (bat.getMovementState() == bat.LEFT) {
            newX -= bat.paddleSpeed/10;
        }

        this.clearObstacleY(bat.getRect().top - 20);
        this.normalizeVelocity(newX, newY);
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

}
