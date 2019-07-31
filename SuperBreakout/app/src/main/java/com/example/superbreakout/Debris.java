package com.example.superbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import java.util.Random;

/*
 *  Falling objects that can destroy the bat, or grant either an upgrade or downgrade
 */
public class Debris {

    // Constant speed variables for each debris type.
    private final float UPGRADE_SPEED = 1100;
    private final float DOWNGRADE_SPEED = 1350;
    private final float HARMFUL_SPEED = 1500;

    private RectF rect; // Square/Rect representing the falling debris

    private String debrisType; // Debris type (Harmful, Upgrade, Downgrade, None)

    private boolean active; // If the debris is in frame or not

    protected Bitmap debrisBitmap;
    protected BitmapDimensions bitmapDimensions;
    Context context;

    /*
     * Debris constructor that takes in the initial coordinates
     * of a destroyed obstacle.
     */
    public Debris(Context context, int row, int column, int width, int height, int horzPadding, int vertPadding) {

        this.context = context;

        rect = new RectF( column * width + horzPadding,
                row * height + vertPadding,
                column * width + width + horzPadding - horzPadding/20,
                row * height + height + vertPadding - vertPadding/15);

        active = false;
    }

    public void setDebrisType(String[] types) {

        Random random = new Random();
        debrisType = types[random.nextInt(types.length)];
    }

    public Bitmap getDebrisBitmap() { return debrisBitmap; }

    public RectF getRect() {
        return this.rect;
    }

    public String getDebrisType() {
        return debrisType;
    }

    public boolean getActive() {
        return active;
    }

    public void activate() {
        // Used once the ball hits an obstacle
        active = true;
        float old_width = rect.width();
        float old_height = rect.height();

        rect.top = rect.top + old_height/8;
        rect.left = rect.left + old_width/4;
        rect.right = rect.right - old_width/4;
        rect.bottom = rect.bottom - old_height/8;

        setDebrisBitmap();

    }

    public void setDebrisBitmap() {
        bitmapDimensions = new BitmapDimensions( (int)rect.width(), (int)rect.height() );
        switch(debrisType) {
            case "Harmful":
                debrisBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.harmful);
                break;
            case "Upgrade":
                debrisBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.upgrade);
                break;
            case "Downgrade":
                debrisBitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.downgrade);
                break;
        }
        debrisBitmap = Bitmap.createScaledBitmap(debrisBitmap, bitmapDimensions.width,
                bitmapDimensions.height, true);
    }

    public void deactivate() {
        // Used once the ball or bat hits a debris
        active = false;
    }

    public void update(long fps, int screenY) {
        // Change the top/bottom coordinates to imitate "falling"
        if(rect.bottom > screenY) {
            deactivate();
        }

        float SPEED = 0f;
        switch (debrisType) {
            case "Harmful":
                SPEED = HARMFUL_SPEED;
                break;
            case "Upgrade":
                SPEED = UPGRADE_SPEED;
                break;
            case "Downgrade":
                SPEED = DOWNGRADE_SPEED;
                break;
            default:
                break;
        }
        rect.top = rect.top + SPEED/fps;
        rect.bottom = rect.bottom + SPEED/fps;
    }

}
