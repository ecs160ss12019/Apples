package com.example.superbreakout;

import android.graphics.RectF;

// Tiles to be hit
public class Obstacle {

    // Variables
    private RectF obstacle; // Holds four float coordinates (left, top, right, bottom) for the obstacle
    private int durability; // Amount of the times the obstacle can be hit by the ball.
    private String effect;  // The obstacle's special effect that can influence adjacent obstacles
                            // Obstacle may or may not have an effect.


    /*
        Obstacle constructor that takes in (x,y) coordinates
        corresponding to its row and column, and its dimensions.
        It'll set the object's durability and generate a random effect for it.
     */
    public Obstacle(float row, float column, float width, float height) {

    }

    /*
     *   This function retrieves the obstacle as a RectF
     */
    public RectF getObstacle() {
        return this.obstacle;
    }

    /* This function updates the new position of the obstacle
     * for the draw function in SuperBreakoutGame to draw
     * the new position of the obstacle
     */
    public void update(long fps) {}

    /*
     *   This function reduces the durability of an obstacle
     *   if it has been hit by the ball.
     */
    public void reduceDurability() {
        this.durability -= 1;
    }

    /*
     *   This function fetches the current durability of the obstacle.
     *   May prove useful when generating varying durabilities.
     */
    public int getDurability() {
        return this.durability;
    }

    /*
     *   This function removes the obstacle from
     *   the game once an obstacle's durability reaches 0.
     */
    public void destroy() {

    }

    /*
     *   This function generates a Debris object within the game
     *   once the obstacle has been destroyed.
     *   The obstacle may or may not drop a Debris object.
     */
    public void generateDebris() {

    }

    /*
     *   This function returns the String name of the effect
     */
    public String getEffect() {
        return this.effect;
    }

    /*
     *   This function applies an effect to the obstacle
     *   given by effect input provided by another obstacle
     */
    public void applyEffect(String effect) {
        switch (effect) {
            case "effectName":
                break;
            // add more effect cases
            default:
                break;
        }
    }
}
