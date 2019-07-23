package com.example.superbreakout;

import android.content.Context;

public class LevelTwo extends Level {

    public static final int LEVEL_TWO = 2;

    public LevelTwo(int x, int y, Context currentContext){
        super(x,y, currentContext);
        level = LEVEL_TWO;
        randomizer = new Randomizer();
    }

    @Override
    public void createBricks(Context context){

    }

    @Override
    public Level advanceNextLevel(){
        return new LevelThree(screenX, screenY, context);
    }

}
