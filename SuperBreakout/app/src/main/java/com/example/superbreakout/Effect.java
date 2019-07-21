package com.example.superbreakout;

import java.util.Random;

public class Effect {

    // parameters that check whether the Effect will be for the ball or the bat
    protected boolean ballEffect;
    protected boolean batEffect;

    public Effect() {
        // Randomly decide which component (bat or ball) to affect
        String[] list = {"Ball", "Bat"};
        Random random = new Random();

        switch(list[random.nextInt(list.length)]) {
            case "Ball":
                ballEffect = true;
                batEffect = false;
                break;
            case "Bat":
                ballEffect = false;
                batEffect = true;
                break;
        }
    }
}

class Upgrade extends Effect {

    public String upgradeName; // Name of the upgrade

    public Upgrade() {
        // Randomly generate upgrade based on Effect booleans

        // Super constructor to determine if its a ball or bat effect
        super();

        // List of Upgrades
        String[] batUpgrades = {"Speed Boost", "Double Size"};
        String[] ballUpgrades = {"Exploding Ball", "Slow Ball"};
        Random random = new Random();

        // Check if its a Ball or Bat upgrade
        if(ballEffect) {
            upgradeName = ballUpgrades[random.nextInt(ballUpgrades.length)];
        } else {
            upgradeName = batUpgrades[random.nextInt(batUpgrades.length)];
        }
    }
}

class Downgrade extends Effect {

    public String downgradeName; // Name of the downgrade

    public Downgrade() {
        // Randomly generate downgrade based on Effect booleans
    }
}
