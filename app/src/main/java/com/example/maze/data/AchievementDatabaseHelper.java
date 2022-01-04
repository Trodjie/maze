package com.example.maze.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.maze.data.DatabaseDescription.Achievement;

public class AchievementDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Achievements.db";
    private static final int DATABASE_VERSION = 1;

    //Конструктор
    public AchievementDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Создание таблицы achievements при создании базы данных
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Команда SQL для создания таблицы achievements
        final String CREATE_ACHIEVEMENT_TABLE =
                "CREATE TABLE " + Achievement.TABLE_NAME + "(" +
                        Achievement._ID + " integer primary key, " +
                        Achievement.COLUMN_PASSING_DATE + " TEXT, " +
                        Achievement.COLUMN_DIFFICULTY_LEVEL + " TEXT, " +
                        Achievement.COLUMN_PASSING_TIME + " TEXT);";
        db.execSQL(CREATE_ACHIEVEMENT_TABLE); // Создание таблицы achievements
    }

    //Определяет способ обновления при изменения схемы бд
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
