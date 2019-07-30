package com.example.superbreakout;


public class Score implements Comparable<Score> {

    public String playerName;
    public int scoreValue;

    public Score(String name, int value) {
        playerName = name;
        scoreValue = value;
    }

    // return 0 if equal
    // return 1 if parameter is greater than this score
    // return -1 if parameter is less than this score
    public int compareTo(Score score) {
        return score.scoreValue > scoreValue? 1: score.scoreValue < scoreValue? -1 : 0;
    }

    public String getScoreText() {
        return playerName + " - " + scoreValue;
    }


}
