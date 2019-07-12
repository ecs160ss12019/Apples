package com.example.superbreakout;

import android.graphics.RectF;

public class Bat {
	
	private final int LEFT = -1;
	private final int STOP = 0;
   	private final int RIGHT = 1;	

	RectF rect; // Object to represent Bat's four corners
	
	private float width; // Width of the bat
	private float height; // Height of the bat

	private float speed; // Speed of the Bat
	private int direction; // The bat's current direction
	
	private int batDPI;	

	/*
	 * @posX - Screen's width
	 * @posY - Screen's height
	 * @DPI - Dots Per Inch of the screen (Pixel Density)
	 *
	 * Instantiate Bat object and place bat's coordinate in the bottom center
	 * Instantiate rect with x, y, width, and height to represent Bat's 4
	 * corners.
	 */
	public Bat(int scrWidth, int scrHeight, int DPI){

		width = DPI/2;
		height = DPI/5;
		batDPI = DPI;

		int x = scrWidth/2;
		int y = scrHeight; // Will need to adjust for better position 
		
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
