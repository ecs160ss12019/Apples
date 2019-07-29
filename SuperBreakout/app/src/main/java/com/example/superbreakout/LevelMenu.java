package com.example.superbreakout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelMenu extends Activity {
    int LevelIndicator = 0;
    
    private int SlideIndicator = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.level_menu);

        final Button level1 = findViewById(R.id.level1);
        level1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent LevelMenu = getIntent();
                LevelIndicator = 1;
            }
        });

        final Button level2 = findViewById(R.id.level2);
        level2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent LevelMenu = getIntent();
                LevelIndicator = 2;
            }
        });

        final Button level3 = findViewById(R.id.level3);
        level3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent LevelMenu = getIntent();
                LevelIndicator = 3;
            }
        });

        final Button level4 = findViewById(R.id.level4);
        level4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent LevelMenu = getIntent();
                LevelIndicator = 4;
            }
        });

        final Button level5 = findViewById(R.id.level5);
        level5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent LevelMenu = getIntent();
                LevelIndicator = 5;
            }
        });

        /**
         * If switch is checked, set the sliding indicator to one and zero otherwise
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

    @Override
    public void onBackPressed() {
        backToMainMenu.putExtra("SI", SlideIndicator);
        super.onBackPressed();
        Intent backToMainMenu = new Intent(LevelMenu.this, SuperBreakoutActivity.class);
        backToMainMenu.putExtra("LevelIndicator", LevelIndicator);
        startActivity(backToMainMenu);
    }
}
