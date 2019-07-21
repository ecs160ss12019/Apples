package com.example.superbreakout;

import java.util.Random;

public class Effect {

    // parameters that check whether the Effect will be for the ball or the bat
    private boolean ballEffect;
    private boolean batEffect;

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
    }
}

class Downgrade extends Effect {

    public String downgradeName; // Name of the downgrade

    public Downgrade() {
        // Randomly generate downgrade based on Effect booleans
    }
}
