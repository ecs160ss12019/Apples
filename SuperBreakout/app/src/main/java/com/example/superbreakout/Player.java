package com.example.superbreakout;

/**
 * This class contains all the information of the player.
 */

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int STARTING_LIVES = 3;
    private final int FREE_LIFE_POINTS = 300;
    int score = 0;
    int consecutiveHits = 0;
    int nextLife = 0;
    int lives;
    private List<Upgrade> activeUpgrades = new ArrayList<Upgrade>();
    private List<Downgrade> activeDowngrades = new ArrayList<Downgrade>();
    String name;

    /**
     * Constructor to set lives of the player per stage.
     */
    public Player(){
        lives = STARTING_LIVES;
    }

    /**
     * Reduces player's life.
     */
    public void reduceLifeByOne(){ lives--;}

    /**
     *
     * @return Score of player currently..
     */
    public int getScore(){ return score;}

    /**
     *
     * @return Lives of player currently.
     */
    public int getLives(){ return lives;}

    /**
     * Adds points if player hits a brick.
     * Adds multiplier if player hits brick continuously.
     * Brings cumulative points to the next level.
     */
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

    /**
     * Resets continuous multiplier if player misses the ball.
     */
    public void missBrick(){
        consecutiveHits = 0;
    }

    /**
     *
     * @return True / False if player is alive or not.
     */
    public boolean isAlive(){
        if(lives == 0) return false;
        else return true;
    }

    /**
     *
     * @return Player's activated upgrades.
     */
    public List<Upgrade> getActiveUpgrades() { return activeUpgrades; }

    /**
     *
     * @return Player's activated downgrades.
     */
    public List<Downgrade> getActiveDowngrades() { return activeDowngrades; }

    /**
     * Updates the effect for a certain period of time.
     * @param bat Bat object of the game.
     * @param ball Ball object of the game.
     */
    public void updateEffects(Bat bat, Ball ball) {

        for (int i = 0; i < activeUpgrades.size(); i++) {
            activeUpgrades.get(i).reduceTimer();
            if (activeUpgrades.get(i).effectTimer == 0) {
                resetEffects(activeUpgrades.get(i).upgradeName, bat, ball);
                activeUpgrades.remove(i);
                i = i - 1;
            }
        }

        for (int i = 0; i < activeDowngrades.size(); i++) {
            activeDowngrades.get(i).reduceTimer();
            if (activeDowngrades.get(i).effectTimer == 0) {
                resetEffects(activeDowngrades.get(i).downgradeName, bat, ball);
                activeDowngrades.remove(i);
                i = i - 1;
            }
        }
    }

    /**
     * Resets the effect of the player to original properties.
     * @param effectName Type of the effect
     * @param bat Bat object of the game.
     * @param ball Ball object of the game.
     */

    public void resetEffects(String effectName, Bat bat, Ball ball) {

        bat.stunTimer = 0;

        switch(effectName) {
            case "SpeedUp":
                bat.setPaddleSpeed(bat.getPaddleSpeed()/2);
                break;
            case "SizeUp":
                bat.resetBatWidth();
                break;
            case "Explosion":
                ball.explosion = false;
                break;
            case "Slow":
                ball.setBallSpeed(ball.getBallSpeed()*2);
                break;
            case "SpeedDown":
                bat.setPaddleSpeed(bat.getPaddleSpeed()*1.25f);
                break;
            case "SizeDown":
                bat.resetBatWidth();
                break;
            case "Hollow":
                ball.hollow = false;
                break;
        }
    }

    public boolean addUpgrade(Upgrade ug) {

        boolean duplicate = false;
        for (int i = 0; i < activeUpgrades.size(); i++) {
            if(activeUpgrades.get(i).upgradeName.equals(ug.upgradeName)) {
                duplicate = true;
                break;
            } else {
                continue;
            }
        }

        if(!duplicate) {
            activeUpgrades.add(ug);
            return true;
        }
        return false;

    }

    public boolean addDowngrade(Downgrade dg) {

        boolean duplicate = false;
        for (int i = 0; i < activeDowngrades.size(); i++) {
            if(activeDowngrades.get(i).downgradeName.equals(dg.downgradeName)) {
                duplicate = true;
                break;
            } else {
                continue;
            }
        }

        if(!duplicate) {
            activeDowngrades.add(dg);
            return true;
        }
        return false;
    }

    public void clearEffects(Bat bat, Ball ball) {

        for (int i = 0; i < activeUpgrades.size(); i++) {
            resetEffects(activeUpgrades.get(i).upgradeName, bat, ball);
        }
        for (int i = 0; i < activeDowngrades.size(); i++) {
            resetEffects(activeDowngrades.get(i).downgradeName, bat, ball);
        }

        activeUpgrades.clear();
        activeDowngrades.clear();
    }

}
