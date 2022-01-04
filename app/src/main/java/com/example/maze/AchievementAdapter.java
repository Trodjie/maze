package com.example.maze;

import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.maze.data.DatabaseDescription.*;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {

    //Переменные экземпляров AchievementsAdapter
    private Cursor cursor = null;
    private final AchievementClickListener clickListener;

    public AchievementAdapter(AchievementClickListener clickListener) {
        this.clickListener = clickListener;
    }

    //Интерфейс реализуется AchievementsFragment для обработки прикосновения к элементу в списке RecyclerView
    public interface AchievementClickListener {
        void onClick(Uri achievementsUri);
    }

    //Вложенный субкласс RecyclerView.ViewHolder используется для реализации
    //паттерна View-Holder в контексте RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        private long rowID;

        //Найстройка объекта ViewHolder элемента RecyclerView
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
            //Присоединение слушаетля к itemView
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickListener.onClick(Achievement.buildAchievementUri(rowID));
                        }
                    }
            );
        }

        //Идентификатор записи базы данных для достижения в ViewHolder
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }
    }

    //Подготовка нового элемента списка и его объекта ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //заполнение макета android.R.layout.simple_list_item_1
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view); //ViewHolder текущего элемента
    }

    //Назначает текст элемента списка
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        int index = cursor.getColumnIndex(Achievement._ID);
        holder.setRowID(cursor.getLong(index));
        int text = cursor.getColumnIndex(Achievement.COLUMN_PASSING_DATE);
        holder.textView.setText(cursor.getString(text));
    }

    //Возвращает количество элементов, предоставляемых адаптером
    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    //Текущий объект Cursor адаптера заменяется новым
    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }
}
