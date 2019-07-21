package com.example.superbreakout;

import java.util.Random;

public class Effect {

    protected String effectTarget; // name of the target that will hold the effect

    public Effect() {
        // Randomly decide which component (bat or ball) to affect
        String[] list = {"Ball", "Bat"};
        Random random = new Random();
        effectTarget = list[random.nextInt(list.length)];
    }
}

class Upgrade extends Effect {

    public String upgradeName; // Name of the upgrade

    public Upgrade() {
        // Randomly generate upgrade based on the effectTarget parameter

        // Super constructor to determine if its a ball or bat effect
        super();

        // List of Upgrades
        String[] batUpgrades = {"Speed", "Double"};
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
        // Randomly generate downgrade based on Effect booleans
    }
}
