package com.example.superbreakout;

/**
 * This class handles all explosive obstacles.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosive extends Obstacle{

    static final int DURABILITY_EXPLOSIVE = -1;
    int numNeighbors;
    Obstacle[] neighborsToDestroy;

    /**
     *
     * @param context Context of GameView.
     * @param row Row of Brick.
     * @param column Column of Brick.
     * @param widthObstacle How large the explosive obstacle is .
     * @param heightObstacle How high the explosive obstacle is.
     * @param horzPadding Horizontal padding.
     * @param vertPadding Vertical padding.
     */
    public Explosive(Context context, int row, int column, int widthObstacle, int heightObstacle,
                          int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);

        numNeighbors = 0;

        durability = DURABILITY_EXPLOSIVE;
        setBricksBitmap();
    }

    /**
     *
     * @param potentialNeighbors Obstacles that are beside the explosive obstacle potentially to be destroyed
     * @param numRows ??
     * @param rowStart ??
     * @param columnStart ??
     * @param rowEnd ??
     * @param columnEnd ??
     */
    public void findNeighbors(Obstacle[] potentialNeighbors, int numRows,
                              int rowStart, int columnStart, int rowEnd, int columnEnd) {

        numNeighbors = (rowEnd - rowStart + 1) * (columnEnd - columnStart + 1);
        this.neighborsToDestroy = new Obstacle[numNeighbors];

        int index = 0;
        for(int c = columnStart; c <= columnEnd; c++) {
            for(int r = rowStart; r <= rowEnd; r++) {
                neighborsToDestroy[index] = potentialNeighbors[c*numRows + r];
                index++;
            }
        }
    }

    /**
     *
     * @param potentialNeighbors Obstacles that are beside the explosive obstacle potentially to be destroyed
     * @param numRows
     * @param numCols
     */
    @Override
    public void setNeighbors(Obstacle[] potentialNeighbors, int numRows, int numCols) {
        int rowStart = this.row - 1;
        int rowEnd = this.row + 1;
        int colStart = this.column - 1;
        int colEnd = this.column + 1;
        if(this.column == 0) {
            colStart = 0;
        }
        if(this.row == 0) {
            rowStart = 0;
        }
        if(this.column == numCols-1) {
            colEnd = this.column;
        }
        if(this.row == numRows-1) {
            rowEnd = this.row;
        }
        this.findNeighbors(potentialNeighbors, numRows, rowStart, colStart, rowEnd, colEnd);

    }

    /**
     *
     * @return Current obstacle that's set to exploded (invisible).
     */
    @Override
    public Obstacle reduceDurability() {
        for(int i = 0; i < this.numNeighbors; i++) {
            neighborsToDestroy[i].setInvisible();
            System.out.println("BOOM");
        }
        return this;
    }

    /**
     * Sets the bitmap for the explosive obstacle
     */
    @Override
    public void setBricksBitmap(){
        // Sets the height of each obstacle's bitmap to 200 x 50 pixels
        bitmapDimensions = new BitmapDimensions(200, 50);

        // TODO find a better image bitmap for explosive tiles
        bricksBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explode);

        bricksBitmap = Bitmap.createScaledBitmap(bricksBitmap, bitmapDimensions.width,
                bitmapDimensions.height, true);
    }
}
