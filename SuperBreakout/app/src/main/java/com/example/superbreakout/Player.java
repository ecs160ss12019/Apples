package com.example.superbreakout;

public class Player {

    private final int STARTING_LIVES = 3;
    int score = 0;
    int lives;

    public Player(){
        lives = STARTING_LIVES;
    }

    public void reduceLifeByOne(){ lives--;}

    public int getScore(){ return score;}

    public int getLives(){ return lives;}

    public boolean isAlive(){
        if(lives == 0) return false;
        else return true;
    }
}
