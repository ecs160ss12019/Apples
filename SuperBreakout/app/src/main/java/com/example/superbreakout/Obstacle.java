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

    public Obstacle(Context context, int row, int column, int widthObstacle, int heightObstacle) {
        super(widthObstacle, heightObstacle);

        isVisible = true;

        // Padding between bricks
        int padding = heightObstacle/5;

        bricksBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_grassed);
        bricksBitmap = Bitmap.createScaledBitmap(bricksBitmap, 200, 50, true);


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
}
