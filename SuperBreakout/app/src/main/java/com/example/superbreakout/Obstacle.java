package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

public abstract class Obstacle extends GameObject {

    private RectF rect;
    private boolean isVisible;
    protected Bitmap bricksBitmap;
    protected BitmapDimensions bitmapDimensions;
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

        rect = new RectF( column * width + horzPadding,
                row * height + vertPadding,
                column * width + width + horzPadding - horzPadding/20,
                row * height + height + vertPadding - vertPadding/15);
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

    public int getDurability(){ return durability;}

    abstract Obstacle reduceDurability();

    abstract void setNeighbors(Obstacle[] potentialNeighbors, int numRows, int numCols);

    abstract void setBricksBitmap();
}
