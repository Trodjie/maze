package com.example.maze.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.maze.R;
import com.example.maze.data.DatabaseDescription.Achievement;

public class AchievementContentProvider extends ContentProvider {

    //Используется для обращения к базе данных
    private AchievementDatabaseHelper dbHelper;

    //UriMatcher помогает ContentProvider определить выполняемую операцию
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //Константы, используемые для определения выполняемой операции
    private static final int ONE_ACHIEVEMENT = 1; //одно достижение
    private static final int ACHIEVEMENTS = 2; //таблица достижений

    //Статический блок для настройки UriMatcher объекта ContentProvider
    static {
        //Uri для достижения с заданным идентификатором
        uriMatcher.addURI(DatabaseDescription.AUTHORY, Achievement.TABLE_NAME + "/#", ONE_ACHIEVEMENT);
        //Uri для таблицы
        uriMatcher.addURI(DatabaseDescription.AUTHORY, Achievement.TABLE_NAME, ACHIEVEMENTS);
    }

    //Вызывается при создании AchievementContentProvider
    @Override
    public boolean onCreate() {
        //Создание объекта AchievementDatabaseHelper
        dbHelper = new AchievementDatabaseHelper(getContext());
        return true; //Объект AchievementDatabaseHelper создан успешно
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    //Получение информации из базы данных
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        //Создание SQLiteQueryBuilder для запроса к таблице achievements
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Achievement.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case ONE_ACHIEVEMENT: //Выбрать достижение с заданным идентификатором
                queryBuilder.appendWhere(
                        Achievement._ID + "=" + uri.getLastPathSegment());
                break;
            case ACHIEVEMENTS: //Выбрать все достижения
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_query_uri) + uri);
        }
        //Выполнить запрос для получения одного или всех достижений
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    //Вставка нового достижения в бд
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newAchievementUri = null;
        switch (uriMatcher.match(uri)) {
            case ACHIEVEMENTS:
                // При успехе возвращается идентификатор записи нового достижения
                long rowId = dbHelper.getWritableDatabase().insert(
                        Achievement.TABLE_NAME, null, values);
                //Если достижение было добавлено, создать подходящий Uri; иначе выдать исключение
                if (rowId > 0) {
                    newAchievementUri = Achievement.buildAchievementUri(rowId);
                    //Оповестить наблюдателей об изменениях в базе данных
                    getContext().getContentResolver().notifyChange(uri, null);
                } else
                    throw new SQLException(
                            getContext().getString(R.string.insert_failed) + uri);
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.insert_failed) + uri);
        }
        return newAchievementUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}