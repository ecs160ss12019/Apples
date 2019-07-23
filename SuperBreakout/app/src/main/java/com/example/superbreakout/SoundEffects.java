package com.example.superbreakout;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

public class SoundEffects {
    SoundPool sp;
    int idFX;

    public SoundEffects(Context context){
        // Instantiate a SoundPool dependent on Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // The new way
            // Build an AudioAttributes object
            AudioAttributes audioAttributes =
                    // First method call
                    new AudioAttributes.Builder()
                            // Second method call
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            // Third method call
                            .setContentType
                                    (AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            // Fourth method call
                            .build();

            // Initialize the SoundPool
            sp = new SoundPool.Builder()
                    .setMaxStreams(1) // sets maximum amount of fx at a single instance to be 3
                    .setAudioAttributes(audioAttributes)
                    .build();
        }

        try{
            // Create objects of the 2 required classes
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor1;
            AssetFileDescriptor descriptor2;
            // Load our fx in memory ready for use
            descriptor1 = assetManager.openFd("blip-1.wav");
            idFX = sp.load(descriptor1, 0);
        } catch(IOException e){
            // Print an error message to the console
            Log.d("Error", "=Failed to load sound files");
        }
    }

    public void playFX() {
        sp.play(idFX, 1, 1, 0, 0, 1);
    }
}
