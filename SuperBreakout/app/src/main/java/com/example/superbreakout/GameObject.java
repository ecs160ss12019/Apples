package com.example.superbreakout;

/**
 * The GameObject class contains all the information to the dimensions
 * of each of the objects that appear in the screen.
 */

public class GameObject {
    protected float width;
    protected float height;

    /**
     * Creates a new instance of GameObject with specified width and height.
     * @param width Width of GameObject.
     * @param height Height of GameObject.
     */
    public GameObject(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     *
     * @param height Set height of GameObject
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     *
     * @return Height of GameObject
     */
    public float getHeight() {
        return this.height;
    }

    /**
     *
     * @param width Set width of GameObject
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     *
     * @return Width of GameObject
     */
    public float getWidth() {
        return this.width;
    }
}

