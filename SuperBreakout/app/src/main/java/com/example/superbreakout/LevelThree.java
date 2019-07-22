package com.example.superbreakout;

import android.content.Context;

public class LevelThree extends Level{

    public static final int LEVEL_THREE = 3;
    public LevelThree(int x, int y){
        super(x,y);
        level = LEVEL_THREE;
    }

    @Override
    public void createBricks(Context context){

    }

    @Override
    public Level advanceNextLevel(){
        // Add Win screen and create Level one again.
        return new LevelOne(screenX, screenY);
    }
}
