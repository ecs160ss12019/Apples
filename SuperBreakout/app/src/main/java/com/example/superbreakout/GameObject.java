package com.example.superbreakout;

/*
* GameObject class
* @param width width of object on screen
* @param height height of an object on screen
*
* This class specifies how high and long an object should be.
* */

public class GameObject {
    protected float width;
    protected float height;

    public GameObject(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return this.height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return this.width;
    }
}

