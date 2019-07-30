package com.example.superbreakout;

import java.util.Random;

public class Effect {

    protected String effectTarget; // name of the target that will hold the effect
    protected int effectTimer = 250; // timer for effects.

    public Effect() {
        // Randomly decide which component (bat or ball) to affect
        String[] list = {"Ball", "Bat"};
        Random random = new Random();
        effectTarget = list[random.nextInt(list.length)];
    }

    public String getEffectTarget() {
        return effectTarget;
    }

    public void reduceTimer() { effectTimer--; }
}

class Upgrade extends Effect {

    public String upgradeName; // Name of the upgrade

    public Upgrade() {
        // Randomly generate upgrade based on the effectTarget parameter

        // Super constructor to determine if its a ball or bat effect
        super();

        // List of Upgrades
        String[] batUpgrades = {"SpeedUp", "SizeUp"};
        String[] ballUpgrades = {"Explosion", "Slow"};
        Random random = new Random();

        // Check if its a Ball or Bat upgrade
        switch(effectTarget) {
            case "Ball":
                upgradeName = ballUpgrades[random.nextInt(ballUpgrades.length)];
                break;
            case "Bat":
                upgradeName = batUpgrades[random.nextInt(batUpgrades.length)];
                break;
        }
    }
}

class Downgrade extends Effect {

    public String downgradeName; // Name of the downgrade

    public Downgrade() {
        // Randomly generate downgrade based on effectTarget parameter

        // Super constructor to determine if its a ball or bat effect
        super();

        // List of Downgrades
        String[] batDowngrades = {"SpeedDown", "SizeDown"};
        String[] ballDowngrades = {"Hollow", "Confusion"};
        Random random = new Random();

        // Check if its a Ball or Bat downgrade
        switch(effectTarget) {
            case "Ball":
                downgradeName = ballDowngrades[random.nextInt(ballDowngrades.length)];
                break;
            case "Bat":
                downgradeName = batDowngrades[random.nextInt(batDowngrades.length)];
                break;
        }
    }
}
