package com.example.superbreakout;

import android.graphics.RectF;


public class Ball {

    // Variables
    RectF rect; // Square that represents a ball

    float xVelocity; // Ball's velocity in the horizontal dimension
    float yVelocity; // Ball's velocity in the vertical dimension

    float width; // Ball's width in terms of pixels
    float height; // Ball's height in terms of pixels

    /* This is a constructor of a ball
     * that takes in an initial coordinate of the ball
     * which should be randomly generated
     */
    public Ball(int screenHorizontalWidth) {
        // Sets the value for x and y velocity for the ball
        xVelocity = 0;
        yVelocity = 0;

        width = screenHorizontalWidth / 50; // sets width thickness of the ball
        height = screenHorizontalWidth / 50; // sets height thickness of the ball

        rect = new RectF(); // creates new rectangle object to draw the rectangle for the objects (ball, bat, obstacle)
        //set velocities
    }

    RectF getRect() {
        return rect; // getter for rectangle
    }

    /* This function updates the new position of the ball
     * for the draw function in SuperBreakoutGame to draw
     * the new position of the ball
     *
     * @fps: frame rate that we are refreshing at
     */
    public void update(long fps) {
        //update frames based on frame rate
    }

    /* This function handles all cases when the ball
     * intersects with the bat, sides or obstacles.
     */
    public void bounce() {
        //bounce off bat, sides (SurfaceHolder??) or obstacles
    }

    /* Reflect the ball to travel in the opposite
     * horizontal direction
     */
    public void reverseXvelocity() {

        xVelocity = -xVelocity;
    }

    /* Reflect the ball to travel in the opposite
     * vertical direction
     */
    public void reverseYvelocity() {

        yVelocity = -yVelocity;
    }

    /* Reset the position of the ball
     *
     * @x: x coordinate of position to be placed
     * @y: y coordinate of position to be placed
     */
    public void placeBall(int x, int y) {
        // Initialise the four points of
        // the rectangle which defines the ball
        rect.left = x;
        rect.top = y / 2;
        rect.right = x + width;
        rect.bottom = y /2 + height;

        // How fast will the ball travel
        // You could vary this to suit
        // You could even increase it as the game progresses
        // to make it harder
        yVelocity = -(y / 3);
        xVelocity = (y / 3);
    }

}