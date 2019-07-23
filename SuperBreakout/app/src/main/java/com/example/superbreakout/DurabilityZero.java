package com.example.superbreakout;

import android.content.Context;

public class DurabilityZero extends Obstacle{

    private int DURABILITY_ZERO = 0;

    public DurabilityZero(Context context, int row, int column, int widthObstacle, int heightObstacle,
                         int horzPadding, int vertPadding) {

        super(context, row, column, widthObstacle, heightObstacle,
                horzPadding, vertPadding);

        durability = DURABILITY_ZERO;
    }

    @Override
    public Obstacle reduceDurability() {
        setInvisible();
        return this;
    }

    public void draw(){
        // Need bitmap for self drawing

    }
}
