package com.example.maze.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Dot implements Drawable {
    private int size;
    protected Point point;
    protected Paint paint;

    public Dot(Point point, Paint paint, int size) {
        this.size = size;
        this.point = point;
        this.paint = paint;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        float cellSize = (float) (rect.right - rect.left) / size;
        canvas.drawRect(
                rect.left + point.x * cellSize,
                rect.top + point.y * cellSize,
                rect.left + point.x * cellSize + cellSize,
                rect.top + point.y * cellSize + cellSize,
                paint);
    }
}
