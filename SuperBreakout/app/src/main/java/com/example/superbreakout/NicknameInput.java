package com.example.superbreakout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NicknameInput extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leaderboard_input);

        // set window size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        final Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                EditText editText = findViewById(R.id.user_input);
                returnIntent.putExtra("NickName", editText.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });


        getWindow().setLayout((int) (width*.5), (int) (height*.8));
    }

}

