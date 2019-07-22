package com.example.superbreakout;

import android.content.Context;

public class LevelTwo extends Level {

    private final int LEVEL_TWO = 2;

    public LevelTwo(int x, int y){
        super(x,y);
        level = LEVEL_TWO;
    }

    @Override
    public void createBricks(Context context){

    }

    @Override
    public Level advanceNextLevel(){
        return new LevelThree(screenX, screenY);
    }

}
