package com.example.superbreakout;


public class Score implements Comparable<Score> {

    private String playerName;
    public int scoreValue;

    public Score(String name, int value) {
        playerName = name;
        scoreValue = value;
    }

    public int compareTo(Score score) {
        
    }


}
