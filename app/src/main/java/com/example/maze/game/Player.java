package com.example.maze.game;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Player extends Dot {

    public Player(Point start, int size) {
        super(start, getPaint(), size);
    }

    public static Paint getPaint(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        return paint;
    }

    public void moveTo(int x, int y) {
        point.x = x;
        point.y = y;
    }

    public int getX(){
        return point.x;
    }
    public int getY(){
        return point.y;
    }

}
