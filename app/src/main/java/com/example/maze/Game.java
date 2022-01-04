package com.example.maze;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import com.example.maze.game.GameManager;
import com.example.maze.game.MazeView;

public class Game extends AppCompatActivity {

    private final String LOG_TAG = "Game_logs";
    private MazeView view;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int gameLevel = getIntent().getIntExtra("level", 15);
        String gameLevelString = getIntent().getStringExtra("levelString");

        GameManager gameManager = new GameManager(this, gameLevel, gameLevelString);
        view = new MazeView(this, gameManager);
        setContentView(view);
        gestureDetector = new GestureDetector(this, gameManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
