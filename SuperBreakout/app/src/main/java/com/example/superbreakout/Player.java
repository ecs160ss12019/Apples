package com.example.superbreakout;

public class Player {

    private final int STARTING_LIVES = 3;
    int score = 0;
    int lives;

    public Player(){
        lives = STARTING_LIVES;
    }

    public int getScore(){ return score;}

    public int getLives(){ return lives;}
}
