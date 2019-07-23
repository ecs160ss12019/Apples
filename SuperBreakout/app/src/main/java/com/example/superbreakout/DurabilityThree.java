package com.example.superbreakout;

import android.content.Context;

public class DurabilityThree extends Obstacle {

    private int DURABILITY_THREE = 3;

    public DurabilityThree(Context context, int row, int column, int widthObstacle, int heightObstacle,
                           int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);
        durability = DURABILITY_THREE;
    }

    @Override
    public Obstacle reduceDurability() {
        return new DurabilityTwo(context, row, column,
                (int)width, (int)height, horzPadding, vertPadding);
    }

    public void draw(){
        // Need bitmap for self drawing

    }
}
