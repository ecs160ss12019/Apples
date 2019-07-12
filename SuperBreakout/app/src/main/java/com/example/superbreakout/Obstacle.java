package com.example.superbreakout;

import android.graphics.RectF;

import android.graphics.RectF;

public class Obstacle {

    private RectF rect;

    private boolean isVisible;

    public Obstacle(int row, int column, int width, int height) {

        isVisible = true;

        // Padding between bricks
        int padding = 0;

        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }

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
