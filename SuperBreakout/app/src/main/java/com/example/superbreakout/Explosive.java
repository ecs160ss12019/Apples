package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosive extends DurabilityZero{

    public Explosive(Context context, int row, int column, int widthObstacle, int heightObstacle,
                          int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);

        durability = DURABILITY_ZERO;
        setBricksBitmap();
    }

    @Override
    public void setBricksBitmap(){
        // Sets the height of each obstacle's bitmap to 200 x 50 pixels
        bitmapDimensions = new BitmapDimensions(200, 50);

        // TODO find a new image bitmap for explosive tiles
        bricksBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.sprite_01);

        bricksBitmap = Bitmap.createScaledBitmap(bricksBitmap, bitmapDimensions.width,
                bitmapDimensions.height, true);
    }
}
