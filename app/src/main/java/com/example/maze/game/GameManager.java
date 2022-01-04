package com.example.maze.game;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.example.maze.Game;
import com.example.maze.Success;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GameManager extends GestureDetector.SimpleOnGestureListener {
    private List<Drawable> drawables = new ArrayList<>();
    private View view;
    private Exit exit;
    private Player player;
    private Maze maze;
    private Rect rect = new Rect();
    private int mazeSize;
    private int screenSize = 0;

    private Game gameActivity;
    private Date timeStart;
    private Date timeEnd;

    private int levelComplete;
    private String gameLevelString;

    public GameManager(Game game, int mazeSize, String gameLevel) {
        createGameManager(game, mazeSize);
        levelComplete = 0;
        timeStart = new Date();
        this.gameLevelString=gameLevel;
    }

    private void createGameManager(Game game, int mazeSize) {
        drawables.clear();

        this.mazeSize = mazeSize;
        maze = new Maze(mazeSize);
        drawables.add(maze);

        player = new Player(maze.getStart(), mazeSize);
        drawables.add(player);

        exit = new Exit(maze.getEnd(), mazeSize);
        drawables.add(exit);

        gameActivity = game;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int diffX = 0, diffY = 0;
        diffX = Math.round(e2.getX() - e1.getX());
        diffY = Math.round(e2.getY() - e1.getY());
        if (Math.abs(diffX) > Math.abs(diffY)) {
            diffX = diffX > 0 ? 1 : -1;
            diffY = 0;
        } else {
            diffX = 0;
            diffY = diffY > 0 ? 1 : -1;
        }
        int stepX = player.getX();
        int stepY = player.getY();

        while (maze.canPlayerMove(stepX + diffX, stepY + diffY)) {
            stepX += diffX;
            stepY += diffY;
            if (diffX != 0) {
                if (maze.canPlayerMove(stepX, stepY + 1) || maze.canPlayerMove(stepX, stepY - 1)) {
                    break;
                }
            }
            if (diffY != 0) {
                if (maze.canPlayerMove(stepX + 1, stepY) || maze.canPlayerMove(stepX - 1, stepY)) {
                    break;
                }
            }
        }
        player.moveTo(stepX, stepY);

        //Уровень пройден
        if (exit.getPoint().equals(player.getPoint())) {

            if (++levelComplete != 3)
                createGameManager(gameActivity, mazeSize);
            else {
                timeEnd = new Date();
                int time = (int) ((timeEnd.getTime() - timeStart.getTime()) / 1000); //время в секундах

                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                Intent successIntent = new Intent(gameActivity.getApplicationContext(), Success.class);
                successIntent.putExtra("time", time);
                successIntent.putExtra("level", gameLevelString);
                successIntent.putExtra("date", date);
                gameActivity.startActivityForResult(successIntent, 0);
                gameActivity.finish();
            }
        }
        view.invalidate(); //перерисовка
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    public void draw(Canvas canvas) {
        for (Drawable drawableItem :
                drawables) {
            drawableItem.draw(canvas, rect);
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setScreenSize(int width, int height) {
        screenSize = Math.min(width, height);
        rect.set((width - screenSize) / 2,
                (height - screenSize) / 2,
                (width + screenSize) / 2,
                (height + screenSize) / 2);
    }
}
