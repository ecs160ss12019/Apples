package com.example.superbreakout;

import android.graphics.RectF;
import java.util.Random;

/*
 *  Falling objects that can destroy the bat, or grant either an upgrade or downgrade
 */
public class Debris {

    // Constant speed variables for each debris type.
    private final float UPGRADE_SPEED = 1500;
    private final float DOWNGRADE_SPEED = 1800;
    private final float HARMFUL_SPEED = 3000;

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
        float SPEED = 0f;
        switch (debrisType) {
            case "Harmful":
                SPEED = HARMFUL_SPEED;
                break;
            case "Upgrade":
                SPEED = UPGRADE_SPEED;
                break;
            case "Downgrade":
                SPEED = DOWNGRADE_SPEED;
                break;
            default:
                break;
        }
        rect.top = rect.top + SPEED/fps;
        rect.bottom = rect.bottom + SPEED/fps;
    }

}
