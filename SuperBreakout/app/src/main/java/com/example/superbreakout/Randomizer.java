package com.example.superbreakout;

import java.util.Random;

public class Randomizer {
    int high, low;

    public Randomizer(int min, int max) {
        this.high = max;
        this.low = min;
    }

    public int getRandNumber() {
        Random generator = new Random();
        return generator.nextInt(high - low) + low;
    }
}
