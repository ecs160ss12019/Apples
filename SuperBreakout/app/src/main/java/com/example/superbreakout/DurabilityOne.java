package com.example.superbreakout;

import android.content.Context;

public class DurabilityOne extends Obstacle {

    private int DURABILITY_ONE = 1;
    public DurabilityOne(Context context, int row, int column, int widthObstacle, int heightObstacle,
                         int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);

        durability = DURABILITY_ONE;
    }

    @Override
    public Obstacle reduceDurability() {
        return new DurabilityZero(context, row, column,
                (int)width, (int)height, horzPadding, vertPadding);
    }

    public void draw(){

    }
}
