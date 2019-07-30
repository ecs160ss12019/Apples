package com.example.superbreakout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Leaderboard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);

        TextView scoreView = (TextView) findViewById(R.id.highscores_list);

        SharedPreferences scorePrefs = getSharedPreferences(SuperBreakoutActivity.HI_SCORES, 0);

        String[] savedScores = scorePrefs.getString("highScores", "").split("\\|");

        StringBuilder scoreBuild = new StringBuilder("");
        for(String score : savedScores) {
            scoreBuild.append(score+"\n");
        }

        scoreView.setText(scoreBuild.toString());
    }
}
