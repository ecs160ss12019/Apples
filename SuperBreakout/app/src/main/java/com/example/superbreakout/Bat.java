package com.example.superbreakout;

import android.graphics.RectF;

public class Bat {

	private final int LEFT = -1;
	private final int STOP = 0;
	private final int RIGHT = 1;	

	RectF rect; // Object to represent Bat's four corners

	private float x;
	private float width; // Width of the bat
	private float scrWidth; // With of the screen to prevent the bat from going off screen

	private float speed; // Speed of the Bat
	private int direction; // The bat's current direction


	/*
	 * @posX - Screen's width
	 * @posY - Screen's height
	 *
	 * Instantiate Bat object and place bat's coordinate in the bottom center
	 * Instantiate rect with x, y, width, and height to represent Bat's 4
	 * corners.
	 */
	public Bat(int scrX, int scrY){

		scrWidth = scrX;

		width = scrWidth/8;
		float height = scrY/80; // Will need to adjust for better position
		x = scrWidth/2; // Start in the middle of the screen
		float y = scrY - height;

		// Can use rect.left for x position
		rect = new RectF(x, y, x + width, y + height);

		speed = 500;
	}

	// Return rect object that represents Bat's 4 corner object
	public RectF getRect(){ return rect;}

	// Return Bat's direction
	public int getDirection(){ return direction;}

	// Change bat direction to left
	public void moveLeft(){ direction = LEFT;}

	// Change bat direction to right
	public void moveRight(){ direction = RIGHT;}

	// Stop bat
	public void moveStop(){ direction = STOP;}

	/*
	 * @fps - frame rate	
	 *
	 * Update Super Breakout View of the Bat  and change the Bat's coordinate
	 * based on the movement chosen.
	 */
	public void update(long fps){
		if(direction == LEFT)
			x -= speed/fps;
		if(direction == RIGHT)
			x += speed/fps;
		if(x < 0)
			x = 0;
		if(x + width > scrWidth)
			x = scrWidth - width;

		rect.left = x;
		rect.right = x + width;
	}

	/* 
	 * @debri - the debri that the Bat hit
	 *
	 * Update Bat based on the debri that the bat hit
	 */
	public void hitDebri(Debris debri){
		// Type of debri make changes to bat locally, adjust bat's powe up
		// table and only allow a buff/debuff to apply once?
	}
}
