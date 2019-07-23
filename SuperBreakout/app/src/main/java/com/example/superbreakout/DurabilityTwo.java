package com.example.superbreakout;

import android.content.Context;

public class DurabilityTwo extends Obstacle {

    private int DURABILITY_TWO = 2;


    public DurabilityTwo(Context context, int row, int column, int widthObstacle, int heightObstacle,
                         int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);
        durability = DURABILITY_TWO;
    }

    @Override
    public Obstacle reduceDurability() {
        return new DurabilityOne(context, row, column,
                (int)width, (int)height, horzPadding, vertPadding);
    }

    @Override
    public void setBricksBitmap(){

    }
}
