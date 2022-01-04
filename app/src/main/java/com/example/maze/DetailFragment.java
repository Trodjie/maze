package com.example.maze;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import com.example.maze.data.DatabaseDescription.*;

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ACHIVEMENTS_LOADER = 0; //Идентифицирует Loader
    private DetailFragmentListener listener; //MainActivity
    private Uri achivementsUri; //Uri  выбранного достижения

    private TextView passingDateTextView;
    private TextView levelTextView;
    private TextView passingTimeTextView;

    //Назначение DetailFragmentListener при присоединении фрагмента
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DetailFragmentListener) context;
    }

    //Удаление DetailFragmentListener при отсоединении фрагмента
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    //Методы обратного вызова, реализованные MainActivity
    public interface DetailFragmentListener {
    }

    //Вызывается при создании представлений фрагмента
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //setHasOptionsMenu(true); //У фрагмента есть команды меню

        //Получение объекта Bundle с аргументами и извлечение Uri
        Bundle arguments = getArguments();
        if (arguments != null)
            achivementsUri = arguments.getParcelable(AchievementActivity.ACHIEVEMENT_URI);

        //Заполнение макета DetailFragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        //Получение компонентов EditText
        passingDateTextView = (TextView) view.findViewById(R.id.passingDateTextView);
        levelTextView = (TextView) view.findViewById(R.id.levelTextView);
        passingTimeTextView = (TextView) view.findViewById(R.id.passingTimeTextView);

        //Загрузка достижения
        getLoaderManager().initLoader(ACHIVEMENTS_LOADER, null, this);
        return view;
    }

    // Вызывается LoaderManager для создания Loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Создание CursorLoader на основании аргумента id; в этом
        // фрагменте только один объект Loader, и команда switch не нужна
        CursorLoader cursorLoader;
        switch (id) {
            case ACHIVEMENTS_LOADER:
                cursorLoader = new CursorLoader(getActivity(),
                        achivementsUri, //Uri отображаемого достижения
                        null, //Все столбцы
                        null, // Все записи
                        null, //Без аргументов
                        null); //Порядок сортировки
                break;
            default:
                cursorLoader = null;
                break;
        }
        return cursorLoader;
    }

    //Вызывается LoaderManager при завершении загрузки
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Если достижение существует в бд, вывести его информацию
        if (data != null && data.moveToFirst()) {
            //Полоучение индекса столбца для каждого элемента данных
            int passingDateIndex = data.getColumnIndex(Achievement.COLUMN_PASSING_DATE);
            int levelIndex = data.getColumnIndex(Achievement.COLUMN_DIFFICULTY_LEVEL);
            int passingTimeIndex = data.getColumnIndex(String.valueOf(Achievement.COLUMN_PASSING_TIME));

            //Заполненение TextView полученными данными
            passingDateTextView.setText(data.getString(passingDateIndex));
            levelTextView.setText(data.getString(levelIndex));
            passingTimeTextView.setText(data.getString(passingTimeIndex));
        }
    }

    //Вызывается LoaderManager при сбросе Loader
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
