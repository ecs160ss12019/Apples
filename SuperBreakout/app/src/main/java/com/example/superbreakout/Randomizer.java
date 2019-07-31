package com.example.superbreakout;

import java.util.Random;

/**
 * Helper class to generate random numbers.
 */
public class Randomizer {
    private static Random generator = new Random();

    private Randomizer() {

    }
    /**
     * Generates random number from specified limit.
     *
     * @param min Minimum number that can be generated.
     * @param max Maximum number that can be generated.
     */
    public static int getRandNumber(int min, int max) {
        return generator.nextInt(max - min) + min;
    }

    /**
     * Gets a random boolean value.
     */
    public static boolean getRandBoolean() {
        return generator.nextBoolean();
    }
}
