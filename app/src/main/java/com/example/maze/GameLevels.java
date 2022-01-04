package com.example.maze;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class GameLevels extends AppCompatActivity {

    private final String LOG_TAG = "GameLevels_logs";

    private int level = 0;
    private int[] levels = {15, 20, 30, 40};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelevels);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Кнопка уровень простой
        Button buttonEasy = (Button) findViewById(R.id.buttonEasy);
        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentGame = new Intent(getApplicationContext(), Game.class);
                    level = levels[0];
                    intentGame.putExtra("level", level);
                    intentGame.putExtra("levelString", getApplicationContext().getResources().getString(R.string.difficulty_easy));

                    startActivity(intentGame);
                    finish();
                } catch (Exception e) {
                    Log.v(LOG_TAG, e.toString());
                }
            }
        });

        //Кнопка уровень стандартный
        Button buttonNormal = (Button) findViewById(R.id.buttonNormal);
        buttonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentGame = new Intent(getApplicationContext(), Game.class);
                    level = levels[1];
                    intentGame.putExtra("level", level);
                    intentGame.putExtra("levelString", getApplicationContext().getResources().getString(R.string.difficulty_normal));

                    startActivity(intentGame);
                    finish();
                } catch (Exception e) {
                    Log.v(LOG_TAG, e.toString());
                }
            }
        });

        //Кнопка уровень сложный
        Button buttonHard = (Button) findViewById(R.id.buttonHard);
        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentGame = new Intent(getApplicationContext(), Game.class);
                    level = levels[2];
                    intentGame.putExtra("level", level);
                    intentGame.putExtra("levelString", getApplicationContext().getResources().getString(R.string.difficulty_hard));

                    startActivity(intentGame);
                    finish();
                } catch (Exception e) {
                    Log.v(LOG_TAG, e.toString());
                }
            }
        });

        //Кнопка уровень очень сложный
        Button buttonVeryHard = (Button) findViewById(R.id.buttonVeryHard);
        buttonVeryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentGame = new Intent(getApplicationContext(), Game.class);
                    level = levels[3];
                    intentGame.putExtra("level", level);
                    intentGame.putExtra("levelString", getApplicationContext().getResources().getString(R.string.difficulty_very_hard));

                    startActivity(intentGame);
                    finish();
                } catch (Exception e) {
                    Log.v(LOG_TAG, e.toString());
                }
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    //кнопка "назад" - возврат в MainActivity
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(mainIntent, 0);
            finish();
            return true;
        } catch (Exception e) {
            Log.v(LOG_TAG, e.toString());
            return false;
        }
    }

    //Системаня кнопка назад (работает также)
    @Override
    public void onBackPressed() {
        try {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(mainIntent, 0);
            finish();
        } catch (Exception e) {
            Log.v(LOG_TAG, e.toString());
        }
    }
}
