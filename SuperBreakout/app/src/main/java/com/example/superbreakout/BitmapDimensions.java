package com.example.superbreakout;

public class BitmapDimensions {
    protected int width;
    protected int height;

    public BitmapDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setBitmapHeight(int bitmapHeight) {
        this.height = bitmapHeight;
    }

    public int getBitmapHeight() {
        return height;
    }

    public void setBitmapWidth(int bitmapWidth) {
        this.width = bitmapWidth;
    }

    public int getBitmapWidth() {
        return width;
    }
}
