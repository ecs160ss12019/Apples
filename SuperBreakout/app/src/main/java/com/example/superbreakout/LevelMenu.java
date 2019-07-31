package com.example.superbreakout;

/**
 * This class manages the UI of the settings menu
 * Gets input to determine which level the user chooses and slide controls.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class LevelMenu extends Activity {
    /**
     * Values to be passed back to superBreakoutActivity.
     */
    private int LevelIndicator = 0;
    private int SlideIndicator = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.level_menu);

        /**
         * These are button event handlers.
         * Decides what level gets returned.
         */
        final Button level1 = findViewById(R.id.level1);
        level1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LevelIndicator = 1;
            }
        });

        final Button level2 = findViewById(R.id.level2);
        level2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LevelIndicator = 2;
            }
        });

        final Button level3 = findViewById(R.id.level3);
        level3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LevelIndicator = 3;
            }
        });

        final Button level4 = findViewById(R.id.level4);
        level4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LevelIndicator = 4;
            }
        });

        final Button level5 = findViewById(R.id.level5);
        level5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LevelIndicator = 5;
            }
        });

        /**
         * If switch is checked, set the sliding indicator to one and zero otherwise.
         */
        final Switch slideSwitcher = findViewById(R.id.toggleSwitch);
        slideSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    System.out.println(1);
                    SlideIndicator = 1;
                }

                else {
                    System.out.println(0);
                    SlideIndicator = 0;
                }
            }
        });
    }

    /**
     * On press of the back button
     * Puts in LevelIndicator and SlideIndicator into the intent and sends it back to superBreakoutActivity
     */
    @Override
    public void onBackPressed() {
        Intent backToMainMenu = new Intent();
        backToMainMenu.putExtra("LI", LevelIndicator);
        backToMainMenu.putExtra("SI", SlideIndicator);

        this.setResult(RESULT_OK, backToMainMenu);
        this.finish();
        super.onBackPressed();
    }
}
