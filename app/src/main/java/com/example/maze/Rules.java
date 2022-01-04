package com.example.maze;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class Rules extends AppCompatActivity {

    private final String LOG_TAG = "Rules_logs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    //кнопка "назад" - возврат в MainActivity
    public boolean onOptionsItemSelected(MenuItem item){
        try {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(mainIntent, 0);
            finish();
            return true;
        }
        catch (Exception e){
            Log.v(LOG_TAG, e.toString());
            return false;
        }
    }

    //Системаня кнопка назад (работает также)
    @Override
    public void onBackPressed(){
        try {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(mainIntent, 0);
            finish();
        }
        catch (Exception e){
            Log.v(LOG_TAG, e.toString());
        }
    }
}
