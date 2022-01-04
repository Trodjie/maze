package com.example.maze.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseDescription {

    //Имя ContentProvider обычно совпадает с именем пакета
    public static final String AUTHORY = "com.example.maze.data";

    //Базовый URI для взаимодействия с ContentProvider
    private static final Uri BASE_CONTENT_UTI = Uri.parse("content://" + AUTHORY);

    public static final class Achievement implements BaseColumns {
        public static final String TABLE_NAME = "achievement"; //Имя таблицы

        //Объект Uri для таблицы achievements
        public static final Uri CONTENT_URI = BASE_CONTENT_UTI.buildUpon()
                .appendPath(TABLE_NAME).build();

        //Имена столбцов таблиц
        public static final String COLUMN_PASSING_DATE = "passing_date";
        public static final String COLUMN_DIFFICULTY_LEVEL = "difficulty_level";
        public static final String COLUMN_PASSING_TIME = "passing_time";

        //Создание Uri для конкретного контакта
        public static Uri buildAchievementUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
