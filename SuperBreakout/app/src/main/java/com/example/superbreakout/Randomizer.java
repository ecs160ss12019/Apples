/**
 * Randomizer class to generate random numbers
 */

package com.example.superbreakout;

import java.util.Random;

public class Randomizer {
    int high, low;

    public Randomizer() {
        this.high = 0;
        this.low = 0;
    }

    // generates random integer
    public int getRandNumber(int min, int max) {
        this.high = max;
        this.low = min;

        Random generator = new Random();
        return generator.nextInt(high - low) + low;
    }

    // generates true or false
    public boolean getRandBoolean() {
        Random generator = new Random();

        return generator.nextBoolean();
    }
}
