package com.example.superbreakout;

import android.graphics.RectF;
import java.util.Random;

/*
 *  Falling objects that can destroy the bat, or grant either an upgrade or downgrade
 */
public class Debris {

    private final int SPEED = 1; // Constant speed (in Y-direction) of debris

    private RectF debris; // Square/Rect representing the falling debris

    private float xPos; // x coordinate of debris
    private float yPos; // y coordinate of debris

    private String debrisType; // Debris type (Harmful, Upgrade, Downgrade)


    /*
     * Debris constructor that takes in the initial coordinates
     * of a destroyed obstacle.
     */
    public Debris(int row, int column, int width, int height) {

        int padding = height/5;

        debris = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);


        // sets the debris type randomly from the 3 types.
        String[] types = {"Harmful", "Upgrade", "Downgrade"};
        Random random = new Random();

        debrisType = types[random.nextInt(types.length)];
    }

    /* This function updates the new position of the debris
     * for the draw function in SuperBreakoutGame to draw
     * the new position of the debris
     */
//    public void update(long fps) {}
//
//    /*
//     *  This function generates a debris object once an obstacle is destroyed
//     */
//    /*public RectF spawnDebris() {
//        switch(this.debrisType) {
//            case "Harmful":
//                // spawn harmful debris that destroys bat
//                break;
//            case "Upgrade":
//                // spawn random upgrade
//                break;
//            case "Downgrade":
//                // spawn random downgrade
//                break;
//        }
//        // returns this.debris depending on its type
//    }*/
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
