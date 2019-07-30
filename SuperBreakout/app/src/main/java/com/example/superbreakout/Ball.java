package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.util.Random;

public class Ball extends GameObject {
    private RectF rect; // rectangle that represents the ball
    public double xVelocity; // horizontal component of velocity (positive in the right direction)
    public double yVelocity; // vertical component of velocity (positive in the downwards direction)
    private double speed; // speed of the ball with formula Math.sqrt(xVelocity^2 + yVelocity^2)
    public boolean explosion = false;
    public boolean hollow = false;

    private Bitmap ballBitmap;
    private BitmapDimensions bitmapDimensions; // specifies the dimensions of the bitmap image
    private int screenX;
    private int screenY;
    private boolean active;
    Context context;

    // Make it a 60 pixel x 60 pixel square
    private static final float ballWidth = 10;
    private static final float ballHeight = 10;


    public Ball(Context context, int screenX, int screenY) {
        super(screenX/40, screenX/40);

        this.screenX = screenX;
        this.screenY = screenY;
        // Ball has issues here
        this.width = screenX/40;
        this.height = screenX/40;

        this.context = context;
        // creates new rectangle object for ball
        rect = new RectF();

        // width and height has to be added by these specific numbers to make ball look proportional
        bitmapDimensions = new BitmapDimensions(screenX/40, screenX/40);
        ballBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.inactiveball);
        ballBitmap = Bitmap.createScaledBitmap(ballBitmap,
                bitmapDimensions.width, bitmapDimensions.height, true);
    }

    /* This function updates the movement of the ball
     *
     * @fps: frame rate at which the ball refreshes
     */

    public void update(long fps) {
        rect.left = rect.left + ((float)xVelocity / fps);
        rect.top = rect.top + ((float)yVelocity / fps);
        rect.right = rect.left + width;
        rect.bottom = rect.top + height;
    }

    /* This function generates a random integer
     * in between @high and @low
     *
     * @high: upper bound of randomly generated integer
     * @low : lower bound of randomly generated integer
     */

    /*
    public int boundedRandomInt(int high, int low) {
        Random generator = new Random();
        return generator.nextInt(high - low) + low;
    }*/

    /* This function sets the ball's velocity
     * at random Vy/Vx ratios and the magnitude
     * of such velocity depends on @level.
     *
     * @level: level of the current game
     */
    public void setRandomVelocity(int level) {
        switch (level) {
            case 1:
                this.setBallSpeed(8000);
                break;
            case 2:
                this.setBallSpeed(9000);
                break;
            case 3:
                this.setBallSpeed(5000);
                break;
            case 4:
                this.setBallSpeed(11000);
                break;
            default:
                this.setBallSpeed(7000);
                break;
        }

        int Vx, Vy; // Proposed horizontal and vertical components of velocity
        // randomly generate a variable that determines if the ball starts by moving left/right
        int xDirection = Randomizer.getRandNumber(1,3);
        if(xDirection >= 2)
            Vx = Randomizer.getRandNumber(4,8);
        else
            Vx = -Randomizer.getRandNumber(4,8);

        Vy = -Randomizer.getRandNumber(10,16); // Always start with upwards velocity

        this.normalizeVelocity(Vx, Vy); // Make velocity constant speed
    }

    /* This function checks if the @bat intersects the ball
     *
     * @bat: a bat object
     */
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

    /* This function normalizes the velocity ratios
     * such that the speed is constant
     *
     * @Vx: horizontal velocity component ratio
     * @Vy: vertical velocity component ratio
     */
    public void normalizeVelocity(double Vx, double Vy) {
        double compensationFactor = this.speed / Math.sqrt((Vy*Vy + Vx*Vx));
        this.xVelocity = compensationFactor * Vx;
        this.yVelocity = compensationFactor * Vy;
    }

    /* This function is executed after the ball reflects the @bat
     * The new velocity of the ball depends on the distance between
     * the intersection point of the ball and the @bat and the midpoint
     * of the @bat.
     * Momentum is also added if the @bat is moving in the same horizontal direction
     * as the ball; momentum is likewise decreased if the @bat is moving in the
     * opposite horizontal direction as the ball.
     *
     * @fraction: (distance between point of intersection and bat) / (@bat length/2)
     * @bat: the bat object
     */
    public void getNewVelocity(float fraction, Bat bat) {
        // reflect y velocity but add interpolated factor of y velocity
        double newY = -this.yVelocity + fraction * this.yVelocity/2;
        // reflect x velocity, keeping it in the same direction and add interpolated factor of x velocity
        double newX =  this.xVelocity - fraction * this.xVelocity/2;

        // Momentum factor / spin
        if (bat.checkMovementStateRight()) {
            newX += bat.getPaddleSpeed()/5;
        }else if (bat.checkMovementStateLeft()){
            newX -= bat.getPaddleSpeed()/5;
        }

        this.clearObstacleY(bat.getRect().top);

        this.normalizeVelocity(newX, newY); // keep it at constant speed
    }

    // a fix for bug in Android RectF Class
    public void clearObstacleY(float y) {
        rect.bottom = y;
        rect.top = y - height;
    }

    // a fix for bug in Android RectF Class
    public void clearObstacleX(float x) {
        rect.left = x;
        rect.right = x + width;
    }

    /* This function resets the position of the ball and sets
     * the ball at random velocities based on the @level (speed)
     *
     * @x: x # pixels
     * @y: y # pixels
     * @level: current level of the game
     */
    public void reset(int x, int y, int level) {

        // Place the ball in the centre of the screen at the bottom
        rect.left = x / 2;
        rect.top = y - 200;
        rect.right = x / 2 + width;
        rect.bottom = y - 100 + height;

        this.setRandomVelocity(level);
    }

    public void setBallLocation(int x, int y, int level){
        // Place the ball in the centre of the screen at the bottom
        rect.left = x ;
        rect.top = y-200;
        rect.right = x + width;
        rect.bottom = y - 100 - height;

        this.setRandomVelocity(level);
        this.xVelocity = 0;
    }

    // This function gets the horizontal center of the ball
    public float getMiddle() {
        return (this.getRect().right - this.getRect().left) / 2;
    }

    // This function gets the rectangle object that represents the ball
    public RectF getRect() {
        return rect;
    }

    // This function returns the bitmap image of the ball
    public Bitmap getBallBitmap() { return ballBitmap; }

    // This function reverses the vertical velocity and adds a little momentum to it
    public void reverseYVelocity() {
        yVelocity = -yVelocity;
        this.normalizeVelocity(this.xVelocity, this.yVelocity);
    }

    // This function reverses the horizontal velocity and adds a little momentum to it
    public void reverseXVelocity() {
        xVelocity = -xVelocity;
        this.normalizeVelocity(this.xVelocity, this.yVelocity);
    }

    public void applyUpgrade(String upgradeName) {

        /*
         * Add remaining upgrade cases
         * implement a way to store list of upgrades
         * List of Effects should reset after each death/level
         */

        switch(upgradeName) {
            case "Explosion":
                explosion = true;
                break;
            case "Slow":
                speed = speed/2;
                break;
        }
    }

    public void applyDowngrade(String downgradeName) {

        switch(downgradeName) {
            case "Hollow":
                hollow = true;
                break;
            case "Slow":
                // Implement code
                break;
        }
    }

    // Setter for ball speed
    // Might be needed for additional features (upgrades or downgrades to ball speed)
    public void setBallSpeed(double ballSpeed) {
        this.speed = ballSpeed;
    }

    public double getBallSpeed() { return this.speed; }

    // Check if the ball hit the walls on the screen to change ball's trajectory
    public void checkWallBounce(){
        // Bounce the ball back when it hits the top of screen
        if (getRect().top < 0) {
            reverseYVelocity();
            clearObstacleY(40);
        }

        // If the ball hits left wall bounce
        if (getRect().left < 0) {
            reverseXVelocity();
            clearObstacleX(2);
        }

        // If the ball hits right wall Velocity
        if (getRect().right > screenX) {
            reverseXVelocity();
            clearObstacleX(screenX - 57);
        }
    }

    public boolean checkBallBatCollision(Bat bat){
        // Check for ball colliding with paddle
        if(this.intersect(bat)) {
            if(!getActive()){
                makeActive();
                setRandomVelocity(3);
            }

            // Interpolate the incoming position for computation of the new Velocity
            float midBall = getMiddle();
            float midBat = bat.getMiddle();
            float fracDisplacementFromMid = (midBall - midBat) / midBat;

            getNewVelocity(fracDisplacementFromMid, bat);

            return true;
        }
        return false;
    }

    public boolean checkMissBall(){
        if (getRect().bottom > screenY) return true;
        return false;
    }

    public boolean getActive(){ return active;}

    public void makeActive(){
        active =  true;
        // loads in asset and turns it into bitmaps
        ballBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        ballBitmap = Bitmap.createScaledBitmap(ballBitmap,
                bitmapDimensions.width, bitmapDimensions.height, true);
    }

    public void playerMissedBall(){ active = false;}
}
