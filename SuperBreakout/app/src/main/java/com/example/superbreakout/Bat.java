package com.example.superbreakout;

import android.graphics.RectF;

public class Bat {
	
	public final int LEFT = -1;
	public final int STOP = 0;
   	public final int RIGHT = 1;	

	RectF rect; // Object to represent Bat's four corners
	
	private float x; // Bottom left coordinate of the Bat
	private float y; // Top coordinate of the Bat
	
	private float width; // Width of the bat
	private float height; // Height of the bat

	private float speed; // Speed of the Bat
	private int direction; // The bat's current direction
	
	/*
	 * @posX - Screen's width
	 * @posY - Screen's height
	 * @DPI - Dots Per Inch of the screen (Pixel Density)
	 *
	 * Instantiate Bat object and place bat's coordinate in the bottom center
	 * Instantiate rect with x, y, width, and height to represent Bat's 4
	 * corners.
	 */
	public Bat(int posX, int posY, int DPI){
	}
	
	// Return rect object that represents Bat's 4 corner object
	public RectF getRect(){ return rect;}
	
	// Return Bat's direction
	public int getDirection(){return direction;}
	
	/*
	 * @direction - change direction of the bat
	 *
	 * setDirection will take change the direction of the Bat, either
	 * moving left, stopping, or moving right.
	 */
	public void setDirection(int direction){
	}
	
	/*
	 * @fps - frame rate	
	 *
	 * Update Super Breakout View of the Bat  and change the Bat's coordinate
	 * based on the movement chosen.
	 */
	public void update(long fps){}	
}
