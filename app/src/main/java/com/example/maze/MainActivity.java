package com.example.maze;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "MainActivity_logs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Кнопка правила
        Button buttonRules = (Button)findViewById(R.id.buttonRules);
        buttonRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentRules = new Intent(MainActivity.this, Rules.class);
                    startActivity(intentRules);
                    finish();
                }catch (Exception e) {
                    Log.v(LOG_TAG, e.toString());
                }
            }
        });

        //Кнопка играть
        Button buttonPlay = (Button)findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentPlay = new Intent(MainActivity.this, GameLevels.class);
                    startActivity(intentPlay);
                    finish();
                }catch (Exception e) {
                    Log.v(LOG_TAG, e.toString());
                }
            }
        });

        //Кнопка достижения
        Button buttonAchivements = (Button)findViewById(R.id.buttonAchievements);
        buttonAchivements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentAchievements = new Intent(MainActivity.this, AchievementActivity.class);
                    startActivity(intentAchievements);
                    finish();
                }catch (Exception e) {
                    Log.v(LOG_TAG, e.toString());
                }
            }
        });
    }
}