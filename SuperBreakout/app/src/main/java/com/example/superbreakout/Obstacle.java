package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import android.graphics.RectF;

public class Obstacle extends GameObject {

    private RectF rect;
    private boolean isVisible;
    private Bitmap bricksBitmap;
    private BitmapDimensions bitmapDimensions;

    public Obstacle(Context context, int row, int column, int widthObstacle, int heightObstacle) {
        super(widthObstacle, heightObstacle);

        isVisible = true;

        // Padding between bricks
        int padding = heightObstacle/5;

        // Sets the height of each obstacle's bitmap to 200 x 50 pixels
        bitmapDimensions = new BitmapDimensions(200, 50);

        bricksBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_grassed);
        bricksBitmap = Bitmap.createScaledBitmap(bricksBitmap, bitmapDimensions.width,  bitmapDimensions.height, true);


        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }

    public Bitmap getBricksBitmap() {return bricksBitmap;}

    public RectF getRect() {
        return this.rect;
    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }

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
