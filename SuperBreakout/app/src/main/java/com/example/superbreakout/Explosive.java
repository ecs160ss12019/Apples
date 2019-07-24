package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosive extends DurabilityZero{

    int numNeighbors;
    Obstacle[] neighborsToDestroy = new Obstacle[numNeighbors];

    public Explosive(Context context, int row, int column, int widthObstacle, int heightObstacle,
                          int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);

        numNeighbors = 0;

        durability = DURABILITY_ZERO;
        setBricksBitmap();
    }

    @Override
    public void setNeighbors(Obstacle[] potentialNeighbors, int numRows) {
        for(int c = this.column-1; c < this.column+1; c++) {
            for(int r = this.row-1; r < this.row+1; r++) {
                if(potentialNeighbors[c*numRows + r].getVisibility()) {
                    neighborsToDestroy[numNeighbors] = potentialNeighbors[c * numRows + r];
                    numNeighbors++;
                }
            }
        }
    }

    @Override
    public Obstacle reduceDurability() {
        for(int i = 0; i < this.numNeighbors; i++) {
            if(neighborsToDestroy[i].getVisibility())
                neighborsToDestroy[i].setInvisible();
        }
        return this;
    }

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
