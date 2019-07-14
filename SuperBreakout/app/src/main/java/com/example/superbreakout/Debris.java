package com.example.superbreakout;

import android.graphics.RectF;
import java.util.Random;

/*
 *  Falling objects that can destroy the bat, or grant either an upgrade or downgrade
 */
public class Debris {

    private final int SPEED = 5; // Constant speed (in Y-direction) of debris

    private RectF rect; // Square/Rect representing the falling debris

    private String debrisType; // Debris type (Harmful, Upgrade, Downgrade, None)

    private boolean active; // If the debris is in frame or not

    private boolean falling;


    /*
     * Debris constructor that takes in the initial coordinates
     * of a destroyed obstacle.
     */
    public Debris(int row, int column, int width, int height) {

        int padding = height/5;

        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);


        // sets the debris type randomly from the 4 types.
        String[] types = {"Harmful", "Upgrade", "Downgrade", "None", "None", "None",
        "None", "None", "None"};
        Random random = new Random();

        debrisType = types[random.nextInt(types.length)];

        active = false;

        falling = false;
    }

    /*
     *  This function generates a debris object once an obstacle is destroyed
     */
    public RectF getRect() {
        return this.rect;
    }

    public String getDebrisType() {
        return debrisType;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive() {
        if (!active) {
            active = true;
        }
    }

    /*
     * The update method will change the top/bottom coordinates of debris\
     * to imitate an object "falling" to the bottom of the screen.
     */
    public void update(long fps) {
        rect.top = rect.top + SPEED;
        rect.bottom = rect.bottom + SPEED;
    }

//    /* This function updates the new position of the debris
//     * for the draw function in SuperBreakoutGame to draw
//     * the new position of the debris
//     */
//    public void update(long fps) {}
//
//
//    /*
//     *  Enables the debris to fall once it spawns
//     */
//    public void fall() {
//        // decrease the debris' yPos depending on SPEED
//    }
//
//    /*
//     *  Destroys the object once it comes in contact
//     *  with the ball, bat, or bottom of the frame
//     */
//    public void destroy() {
//        // remove debris from frame/game
//    }
//
//    /*
//     *  Checks whether the debris is hit by the bat or ball
//     */
//    public void hit() {
//        // if hit by the ball, call destroy()
//        // if hit by the bat, apply effects depending on debrisType
//    }


}
