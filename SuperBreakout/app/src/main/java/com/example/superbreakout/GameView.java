package com.example.superbreakout;

/* Sources:
 * https://code.tutsplus.com/tutorials/android-sdk-create-an-arithmetic-game-high-scores-and-state-data--mobile-18825\
 * https://github.com/PacktPublishing/Learning-Java-by-Building-Android-Games-Second-Edition/tree/master/Chapter11
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Typeface;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.List;

public class GameView extends SurfaceView implements Runnable {

    // OS stuff
    Thread gameThread = null;
    SurfaceHolder ourHolder;
    Canvas canvas;
    Paint paint;
    long fps; // sets the frame rate for the game

    volatile boolean playing;
    boolean paused = true;
    boolean gameOver = false;

    // Resolution of screen
    int screenX;
    int screenY;

    // Objects of the game
    Bat bat;
    Player player;
    Level level;

    // Screen items
    Rect dest;
    DisplayMetrics dm;
    int densityDpi;

    Randomizer randomizer;
    Bitmap backgroundImage;

    // File to score highscores
    private SharedPreferences hiScores;

    public static int levelIndicator = 1;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }


    public GameView(Context context, int x, int y, SharedPreferences hs) {
        super(context);

        ourHolder = getHolder();
        paint = new Paint();
        Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/Pacifico.ttf");
        paint.setTypeface(type);

        Resources res = getContext().getResources();


        backgroundImage = BitmapFactory.decodeResource(res, R.drawable.hills_layer_1);
        canvas = new Canvas();

        screenX = x;
        screenY = y;

        dm = context.getResources().getDisplayMetrics();
        densityDpi = dm.densityDpi;

        randomizer = new Randomizer();

        hiScores = hs;
    }

    /**
     * Starts an new instance of SuperBreakout
     */
    public void startNewGame() {
        player = new Player();
        switch(levelIndicator) {
            case 2:
                level = new LevelTwo(screenX, screenY, getContext());
                break;
            case 3:
                level = new LevelThree(screenX, screenY, getContext());
                break;
            case 4:
                level = new LevelFour(screenX, screenY, getContext());
                break;
            case 5:
                level = new LevelFive(screenX, screenY, getContext());
                break;
            default:
                level = new LevelOne(screenX, screenY, getContext());
                break;
        }

        level.createBricks(getContext());
        bat = new Bat(getContext(),screenX, screenY, densityDpi);
        bat.reset(level.getLevel());
    }


    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();

            if (!paused) {
                update();
            }
            draw();

            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 10000 / timeThisFrame;
            }
        }
    }


    public void update() {

        bat.update(fps);
        level.update(fps, bat, player);

        if (!level.atLeastOneBallAlive()) {
            paused = true;
            if (player.isAlive()) {
                level.resetLevel();
                level.resetEffects(player, bat);
            }

        }else{
            if (level.levelCompleted()) {
                level = level.advanceNextLevel();
                level.createBricks(getContext());
                level.resetEffects(player, bat);
            }
        }
    }

    // Draw the newly updated scene
    public void draw() {
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Gets current context
            Resources res = getContext().getResources();

            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();
            // Gets resources for background images
            Bitmap backgroundImage = BitmapFactory.decodeResource(res, R.drawable.hills_layer_1);
            //Bitmap clouds = BitmapFactory.decodeResource(res, R.drawable.clouds);

            // Gets background dimensions
            dest = new Rect(0, 0, getWidth(), getHeight());

            // Draws background image
            canvas.drawBitmap(backgroundImage, null, dest, paint);

            drawBat();
            drawStage();
            drawStats();
            level.drawBall(canvas);
            checkAndDrawWinScreen();
            // Draw everything to the screen
            if (!checkAndDrawEndGame()) {
                ourHolder.unlockCanvasAndPost((canvas));
            }
        }
    }

    // If GameActivity is paused/stoppedq
    // shutdown our thread.
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    // If GameActivity is started
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: // Player has touched the screen
                if (player.isAlive()) {
                    paused = false;
                }
                bat.move(motionEvent.getX());
                break;

            case MotionEvent.ACTION_UP: // Player doesn't touch screen
                bat.stopMoving();
                break;

            case MotionEvent.ACTION_MOVE:
                if (player.isAlive()) {
                    paused = false;
                }
                bat.move(motionEvent.getX());
                break;
        }
        return true;

    }

    /************ HELPER FUNCTIONS ************/

    private boolean checkAndDrawEndGame() {
        if (!player.isAlive()) {
            paint.setTextSize(200);
            paint.setARGB(255,144,12,12);
            canvas.drawText("Game Over!",
                    screenX / 2 - densityDpi , (screenY / 2), paint);
            ourHolder.unlockCanvasAndPost(canvas);

            setHighScore();

            try {
                // Wait 3 seconds then reset a new game
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            startNewGame();

            return true;
        }
        return false;
    }

    private void drawStats() {
        // Choose the brush color for drawing
        paint.setColor(Color.argb(255, 8, 8, 8));
        paint.setTextSize(50);

        // Score Text
        canvas.drawText(
                "Score: " + player.getScore()
                , (densityDpi / 5)-49,180, paint);

        // Lives Text
        canvas.drawText("Lives:  " + player.getLives()
                , (densityDpi / 5)-40, 120, paint);

        // Levels Text
        canvas.drawText("Level:  " + level.getLevel()
                , (densityDpi / 5)-40, 60, paint);

        // Upgrade/Downgrade text
        canvas.drawText("Effects:",(densityDpi/5)-40, 240, paint );

        List<Upgrade> playerUpgrades = player.getActiveUpgrades();
        List<Downgrade> playerDowngrades = player.getActiveDowngrades();
        int y = 240;
        for (int i = 0; i < playerUpgrades.size(); i++) {
            y += 60;
            canvas.drawText(playerUpgrades.get(i).upgradeName, (densityDpi/5)-40, y, paint);
        }

        for (int i = 0; i < playerDowngrades.size(); i++) {
            y += 60;
            canvas.drawText(playerDowngrades.get(i).downgradeName, (densityDpi/5)-40, y, paint);
        }





    }

    private void checkAndDrawWinScreen() {
        if (level.getLevel() == LevelFive.LEVEL_FIVE && level.levelCompleted()) {
            paint.setColor(getResources().getColor(R.color.colorAccent));
            canvas.drawText("You got home!", screenX / 2 - (densityDpi / 1.90f), screenY / 2 +
                    (densityDpi / 1), paint);
            setHighScore();
        }

    }

    private void drawStage() {
        // Draw stage
        paint.setColor(Color.argb(255, 255, 0, 0));
        level.draw(canvas, paint);
    }

    private void drawBat() {
        canvas.drawBitmap(bat.getBatBitmap(), bat.getRect().left, bat.getRect().top, null);
    }

    private void setHighScore() {
        int currentScore = player.getScore();

        SharedPreferences.Editor scoreEditor = hiScores.edit();
        String scores = hiScores.getString("highScores", "");

        if(scores.length() > 0) {
            // there are existing scores
            List<Score> scoreStrings = new ArrayList<Score>();
            String[] exScores = scores.split("\\|"); // Split strings

            // Add scores to the list in specified format
            for(String eSc : exScores) {
                String[] parts = eSc.split(" - ");
                scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
            }

            // Make a new score object with current player's score
            Score newScore = new Score(player.name, currentScore);
            System.out.println(newScore.playerName + " - " + newScore.scoreValue);
            scoreStrings.add(newScore);

            //Sort scores
            Collections.sort(scoreStrings);

            StringBuilder scoreString = new StringBuilder("");
            for(int i = 0; i < scoreStrings.size(); i++) {
                if(i >= 5) break; // we only store top 5 scores
                if(i > 0) scoreString.append("|"); // separate different high scores
                scoreString.append(scoreStrings.get(i).getScoreText());
            }

            scoreEditor.putString("highScores", scoreString.toString());
            scoreEditor.commit();
        }
        else {
            // There are no existing scores
            scoreEditor.putString("highScores", "" + player.name + " - " + currentScore);
            scoreEditor.commit();
        }

        return;
    }
}
