package com.example.superbreakout;

/**
 * Music by Eric Matyas
 * Creates and runs the service for running the background music.
 * Code is based from StackOverflow
 * Link: https://stackoverflow.com/questions/46838443/play-music-with-background-service
 * https://stackoverflow.com/questions/5215459/android-mediaplayer-setvolume-function
 */

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusic extends Service {
    private static final String TAG = null;
    MediaPlayer player;

    /**
     * Initializes MediaPlayer.
     * Loops the music.
     * Sets the volume to 50 percent.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.puzzledreams);
        player.setLooping(true);
        player.setVolume(50,50);
    }

    /**
     * Starts the media player.
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Stops the media player on closing the application.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }
}
