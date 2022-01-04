package com.example.maze;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maze.data.DatabaseDescription.Achievement;

public class AchievementFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //Идентификатор Loader
    private static final int ACHIVEMENTS_LOADER = 0;

    //Сообщает MainActivity о выборе достижения
    private AchivementsFragmentListener listener;

    //Адаптер для recyclerView
    private AchievementAdapter achievementAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true); //У фрагмента есть команды меню

        //Заполнение GUI и получение ссылки на RecyclerView
        View view = inflater.inflate(R.layout.fragment_achivements, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAchivements);

        //recyclerView выводит элементы в вертикальном списке
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        //создание адаптера recyclerView и слушателя щелчков на элементах
        achievementAdapter = new AchievementAdapter(new AchievementAdapter.AchievementClickListener() {
            @Override
            public void onClick(Uri achivementsUri) {
                listener.onAchievementSelected(achivementsUri);
            }
        }
        );

        recyclerView.setAdapter(achievementAdapter); //Назначение адаптера

        //Присоединение ItemDecorator для вывода разделителей
        recyclerView.addItemDecoration(new ItemDivider(getContext()));

        //Улучшает быстродействие, если размер макета RecyclerView не изменяется
        recyclerView.setHasFixedSize(true);

        return view;
    }

    //Присваивание AchivementsFragment при присоединении фрагмента
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AchivementsFragmentListener) context;
    }

    //Удаление AchivementsFragment при отсоединении фрагмента
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    //Инициализация Loader при создании активности этого фрагмента
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ACHIVEMENTS_LOADER, null, this);
    }

    //Вызывается из MainActivity при обновлении базы данных другим фрагментом
    public void updateAchivementsList() {
        achievementAdapter.notifyDataSetChanged();
    }

    //Вызывается LoaderManager для создания Loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //Созданиек CursorLoader на основании аргумента id;
        //в этом фрагменте только один объект Loader, и команда switch не нужна
        switch (id) {
            case ACHIVEMENTS_LOADER:
                return new CursorLoader(getActivity(),
                        Achievement.CONTENT_URI, //Uri таблицы booking
                        null,
                        null,
                        null,
                        Achievement.COLUMN_PASSING_DATE + " COLLATE NOCASE ASC "
                ); //сортировка
            default:
                return null;
        }
    }

    //Вызывается LoaderManager при завершении загрузки
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        achievementAdapter.swapCursor(data);
    }

    //Вызывается LoaderManager при сбросе Loader
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        achievementAdapter.swapCursor(null);
    }

    //Метод обратного вызова, реализуемый MainActivity
    public interface AchivementsFragmentListener {
        //Вызывается при выборе достижения
        void onAchievementSelected(Uri achivementsUri);

        //Вызывается при нажатии кнопки добавления
        void onAddAchievement();
    }
}