package com.example.superbreakout;

import android.graphics.RectF;
import java.util.Random;

/*
 *  Falling objects that can destroy the bat, or grant either an upgrade or downgrade
 */
public class Debris {

    private final float SPEED = 2.5f; // Constant speed (in Y-direction) of debris

    private RectF rect; // Square/Rect representing the falling debris

    private String debrisType; // Debris type (Harmful, Upgrade, Downgrade, None)

    private boolean active; // If the debris is in frame or not

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
        String[] types = {"Harmful", "Upgrade", "Downgrade", "None", "None", "None"};
        Random random = new Random();

        debrisType = types[random.nextInt(types.length)];

        active = false;
    }

    public RectF getRect() {
        return this.rect;
    }

    public String getDebrisType() {
        return debrisType;
    }

    public boolean getActive() {
        return active;
    }

    public void activate() {
        // Used once the ball hits an obstacle
        active = true;
        rect.top = rect.top + rect.height()/2;
        rect.left = rect.left + rect.width()/3;
        rect.right = rect.right - rect.width()/3;
        rect.bottom = rect.bottom - rect.height()/2;
    }

    public void deactivate() {
        // Used once the ball or bat hits a debris
        active = false;
    }

    public void update(long fps) {
        // Change the top/bottom coordinates to imitate "falling"
        rect.top = rect.top + SPEED;
        rect.bottom = rect.bottom + SPEED;
    }

}
