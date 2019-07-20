package com.example.superbreakout;

public class Effect {

    // parameters that check whether the Effect will be for the ball or the bat
    private boolean ballEffect;
    private boolean batEffect;

    public Effect() {
        // Randomly decide which component (bat or ball) to affect
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
