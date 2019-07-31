package com.example.superbreakout;

/**
 * Durability state for durability zero.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DurabilityZero extends Obstacle{

    public static final int DURABILITY_ZERO = 0;

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
    public DurabilityZero(Context context, int row, int column, int widthObstacle, int heightObstacle,
                         int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);

        durability = DURABILITY_ZERO;
        setBricksBitmap();
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
     *
     * @return Same durability state.
     */
    @Override
    public Obstacle reduceDurability() {
        return this;
    }

    /**
     * Sets brick bitmap for durability of 0.
     */
    @Override
    public void setBricksBitmap(){
        // Sets the height of each obstacle's bitmap to 200 x 50 pixels
        bitmapDimensions = new BitmapDimensions((int) width, (int) height);

        bricksBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.tileone);

        bricksBitmap = Bitmap.createScaledBitmap(bricksBitmap, bitmapDimensions.width,
                bitmapDimensions.height, true);
    }

}
