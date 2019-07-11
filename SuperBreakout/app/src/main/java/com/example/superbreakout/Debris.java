package com.example.superbreakout;
import android.graphics.RectF;

/*
 *  Falling objects that can destroy the bat, or grant either an upgrade or downgrade
 */
public class Debris {

    private final int SPEED = 1; // Constant speed (in Y-direction) of debris

    private RectF debris; // Square/Rect representing the falling debris
    private float width;  // Debris' width in terms of pixels
    private float height; // Debris' height in terms of pixels

    private float xPos; // x coordinate of debris
    private float yPos; // y coordinate of debris

    private String debrisType; // Debris type (Harmful, Upgrade, Downgrade)


    /*
     * Debris constructor that takes in the initial coordinates
     * of a destroyed obstacle.
     */
    public Debris() {
        // sets debrisType
    }

    /*
     *  This function generates a debris object once an obstacle is destroyed
     */
    public RectF spawnDebris() {
        switch(this.debrisType) {
            case "Harmful":
                // spawn harmful debris that destroys bat
                break;
            case "Upgrade":
                // spawn random upgrade
                break;
            case "Downgrade":
                // spawn random downgrade
                break;
        }
        // returns this.debris depending on its type
    }

    /*
     *  Enables the debris to fall once it spawns
     */
    public void fall() {
        // decrease the debris' yPos depending on SPEED
    }

    /*
     *  Destroys the object once it comes in contact
     *  with the ball, bat, or bottom of the frame
     */
    public void destroy() {
        // remove debris from frame/game
    }

    /*
     *  Checks whether the debris is hit by the bat or ball
     */
    public void hit() {
        // if hit by the ball, call destroy()
        // if hit by the bat, apply effects depending on debrisType
    }


}
