package com.example.superbreakout;

import android.content.Context;

public class LevelThree extends Level{

    public LevelThree(Context context, int x, int y){
        super(context, x,y);
        level = 3;
    }

    @Override
    public void createBricks(Context context){

    }

    @Override
    public Level advanceNextLevel(){
        // Add Win screen and create Level one again.
        return new LevelOne(context,screenX, screenY);
    }
}
