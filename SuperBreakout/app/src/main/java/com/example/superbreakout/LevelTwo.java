package com.example.superbreakout;

import android.content.Context;

public class LevelTwo extends Level {

    public LevelTwo(Context context, int x, int y){
        super(context, x,y);
        level = 2;
    }

    @Override
    public void createBricks(Context context){

    }

    @Override
    public Level advanceNextLevel(){
        return new LevelThree(context,screenX, screenY);
    }

}
