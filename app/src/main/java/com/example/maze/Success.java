package com.example.maze;

import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import com.example.maze.data.AchievementDatabaseHelper;
import com.example.maze.data.DatabaseDescription;

public class Success extends AppCompatActivity {

    private final String LOG_TAG = "Success_logs";
    private AchievementDatabaseHelper dbHelper;
    private String passingDate;
    private String level;
    private Integer passingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        passingTime = getIntent().getIntExtra("time", 1);
        level = getIntent().getStringExtra("level");
        passingDate = getIntent().getStringExtra("date");

        TextView resultTime = findViewById(R.id.textViewPassingTimeValue);
        resultTime.setText(String.valueOf(passingTime) + " " + getApplicationContext().getResources().getString(R.string.seconds));

        //Кнопка играть
        Button buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentPlay = new Intent(Success.this, GameLevels.class);
                    startActivity(intentPlay);
                    finish();
                } catch (Exception e) {
                    Log.v(LOG_TAG, e.toString());
                }
            }
        });

        //объект для работы с БД
        dbHelper = new AchievementDatabaseHelper(this);
        //Кнопка сохранить и играть
        Button buttonSaveAndPlay = (Button) findViewById(R.id.buttonSaveAndPlay);
        buttonSaveAndPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put("passing_date", passingDate);
                contentValues.put("difficulty_level", level);
                contentValues.put("passing_time", passingTime);

                //вставляем запись и получаем ее ID
                long rowID = db.insert(DatabaseDescription.Achievement.TABLE_NAME, null, contentValues);
                Log.v(LOG_TAG, "row inserted, ID = " + rowID);
                dbHelper.close();

                try {
                    Intent intentPlay = new Intent(Success.this, GameLevels.class);
                    startActivity(intentPlay);
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
