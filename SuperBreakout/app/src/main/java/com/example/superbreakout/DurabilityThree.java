package com.example.superbreakout;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class DurabilityThree extends Obstacle {

    public static final int DURABILITY_THREE = 3;

    public DurabilityThree(Context context, int row, int column, int widthObstacle, int heightObstacle,
                           int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);

        durability = DURABILITY_THREE;
        setBricksBitmap();
    }

    @Override
    public Obstacle reduceDurability() {
        return new DurabilityTwo(context, row, column,
                (int)width, (int)height, horzPadding, vertPadding);
    }

    @Override
    public void setNeighbors(Obstacle[] potentialNeighbors, int numRows) {
        // do nothing
    }

    @Override
    public void setBricksBitmap(){
        // Sets the height of each obstacle's bitmap to 200 x 50 pixels
        bitmapDimensions = new BitmapDimensions(200, 50);

        bricksBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.sprite_09);

        bricksBitmap = Bitmap.createScaledBitmap(bricksBitmap, bitmapDimensions.width,
                bitmapDimensions.height, true);
    }
}
