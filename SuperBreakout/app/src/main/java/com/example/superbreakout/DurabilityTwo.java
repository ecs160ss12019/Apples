package com.example.superbreakout;

/**
 * Durability state for durability of 2.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DurabilityTwo extends Obstacle {

    public static final int DURABILITY_TWO = 2;

    /**
     * Default Constructor.
     * @param context
     * @param row
     * @param column
     * @param widthObstacle
     * @param heightObstacle
     * @param horzPadding
     * @param vertPadding
     */
    public DurabilityTwo(Context context, int row, int column, int widthObstacle, int heightObstacle,
                         int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);
        durability = DURABILITY_TWO;
        setBricksBitmap();
    }

    /**
     *
     * @return Durability State 1.
     */
    @Override
    public Obstacle reduceDurability() {
        return new DurabilityOne(context, row, column,
                (int)width, (int)height, horzPadding, vertPadding);
    }

    /**
     *
     * @param potentialNeighbors
     * @param numRows
     * @param numCols
     */
    @Override
    public void setNeighbors(Obstacle[] potentialNeighbors, int numRows, int numCols) {
        // do nothing
    }

    /**
     * Sets the bitmap for durability state 2.
     */
    @Override
    public void setBricksBitmap(){
        // Sets the height of each obstacle's bitmap to 200 x 50 pixels
        bitmapDimensions = new BitmapDimensions((int) width, (int) height);

        bricksBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.tilethree);

        bricksBitmap = Bitmap.createScaledBitmap(bricksBitmap, bitmapDimensions.width,
                bitmapDimensions.height, true);
    }
}
