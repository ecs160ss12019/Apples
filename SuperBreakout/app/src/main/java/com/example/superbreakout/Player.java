package com.example.superbreakout;

public class Player {

    private final int STARTING_LIVES = 3;
    private final int FREE_LIFE_POINTS = 100;
    int score = 0;
    int consecutiveHits = 0;
    int nextLife = 0;
    int lives;

    public Player(){
        lives = STARTING_LIVES;
    }

    public void reduceLifeByOne(){ lives--;}

    public int getScore(){ return score;}

    public int getLives(){ return lives;}

    public void hitBrick(){
        consecutiveHits += 1;
        score += (int)(10.0*(float)(consecutiveHits/10.0));
        nextLife += (int)(10.0*(float)(consecutiveHits/10.0));
        if(nextLife >= FREE_LIFE_POINTS){
            int residualPoints = nextLife%FREE_LIFE_POINTS;
            lives += nextLife/FREE_LIFE_POINTS;
            nextLife = residualPoints;
        }
    }

    public void missBrick(){
        consecutiveHits = 0;
    }

    public boolean isAlive(){
        if(lives == 0) return false;
        else return true;
    }
}
