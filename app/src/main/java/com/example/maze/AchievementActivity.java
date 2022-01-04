package com.example.maze;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import java.io.Serializable;

public class AchievementActivity extends AppCompatActivity implements AchievementFragment.AchivementsFragmentListener,
        DetailFragment.DetailFragmentListener,
        Serializable {

    //Ключ для сохранения Uri достижения в переданном объекте Bundle
    public static final String ACHIEVEMENT_URI = "achivement_uri";
    private AchievementFragment achievementFragment; //Вывод списка достижений

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
            return false;
        }
    }

    //Отображает AchievementsFragment при первой загрузке MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achivements);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Создание AchivementsFragment
        achievementFragment = new AchievementFragment();

        //Добавление фрагмента в FrameLayot
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, achievementFragment);
        transaction.commit(); //Вывод AchivementsFragment
    }

    //Отображение DetailFragment для выбранного достижения
    @Override
    public void onAchievementSelected(Uri achivementsUri) {
        displayAchivement(achivementsUri, R.id.fragmentContainer);
    }

    @Override
    public void onAddAchievement() {

    }

    //Отображение информации о достижении
    private void displayAchivement(Uri achievementsUri, int viewID) {
        DetailFragment detailFragment = new DetailFragment();

        //Передача Uri достижения в аргументе DetailFragment
        Bundle arguments = new Bundle();
        arguments.putParcelable(ACHIEVEMENT_URI, achievementsUri);
        detailFragment.setArguments(arguments);

        //Использование FragmentTransaction для отображения
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(viewID, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit(); //Приводит к отображению DetailFragment
    }
}
