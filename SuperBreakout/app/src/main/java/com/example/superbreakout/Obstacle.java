package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import android.graphics.RectF;

public abstract class Obstacle extends GameObject {

    private RectF rect;
    private boolean isVisible;
    private Bitmap bricksBitmap;
    private BitmapDimensions bitmapDimensions;
    protected int row;
    protected int column;
    protected int horzPadding;
    protected int vertPadding;
    protected int durability;
    Context context;

    public Obstacle(Context context, int row, int column, int widthObstacle, int heightObstacle,
                    int horzPadding, int vertPadding) {
        super(widthObstacle, heightObstacle);

        this.row = row;
        this.column = column;
        this.horzPadding = horzPadding;
        this.vertPadding = vertPadding;
        this.context = context;
        isVisible = true;

        // Sets the height of each obstacle's bitmap to 200 x 50 pixels
        bitmapDimensions = new BitmapDimensions(200, 50);

        bricksBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_grassed);
        bricksBitmap = Bitmap.createScaledBitmap(bricksBitmap, bitmapDimensions.width,  bitmapDimensions.height, true);


        rect = new RectF( column * width + horzPadding,
                row * height + vertPadding,
                column * width + width - horzPadding,
                row * height + height - vertPadding);
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

    public int getDurability(){
        return durability;
    }

    // Setter for durability
    public Obstacle setDurability(int obstacleDurability) {
        switch (obstacleDurability){
            default:
                return new DurabilityZero(context,row,column,
                        (int)height,(int)width,horzPadding,vertPadding);
            case 1:
                return new DurabilityOne(context,row,column,
                        (int)height,(int)width,horzPadding,vertPadding);
            case 2:
                return new DurabilityTwo(context,row,column,
                        (int)height,(int)width,horzPadding,vertPadding);
            case 3:
                return new DurabilityThree(context,row,column,
                        (int)height,(int)width,horzPadding,vertPadding);

        }
    }

    abstract Obstacle reduceDurability();

}
