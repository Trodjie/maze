package com.example.maze.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Maze implements Drawable {

    private Paint wallPaint;
    private final boolean[][] array;
    private final int size;
    private final Point end = new Point(1, 1);
    private int bestScore = 0;
    private Point start;

    public Maze(int size) {
        wallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wallPaint.setColor(Color.parseColor("#7f6791"));
        this.size = size;
        array = new boolean[size][size];
        generateMaze();
    }

    public Point getStart() {
        return start;
    }
    public Point getEnd() {
        return end;
    }

    private void generateMaze() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                array[i][j] = i % 2 != 0 && j % 2 != 0 && i < size - 1 && j < size - 1;

            }
        }
        Random random = new Random();
        Stack<Point> stack = new Stack<>();
        stack.push(end);
        while (stack.size() > 0) {
            Point current = stack.peek();
            List<Point> unusedNeighbors = new LinkedList<>();
            //проверка левого соседа
            if (current.x > 2) {
                if (!isUsedCell(current.x - 2, current.y)) {
                    unusedNeighbors.add(new Point(current.x - 2, current.y));
                }
            }
            //проверка верхнего соседа
            if (current.y > 2) {
                if (!isUsedCell(current.x, current.y - 2)) {
                    unusedNeighbors.add(new Point(current.x, current.y - 2));
                }
            }
            //проверка правого соседа
            if (current.x < size - 2) {
                if (!isUsedCell(current.x + 2, current.y)) {
                    unusedNeighbors.add(new Point(current.x + 2, current.y));
                }
            }
            //проверка нижнего соседа
            if (current.y < size - 2) {
                if (!isUsedCell(current.x, current.y + 2)) {
                    unusedNeighbors.add(new Point(current.x, current.y + 2));
                }
            }
            if (unusedNeighbors.size() > 0) {
                int rnd = random.nextInt(unusedNeighbors.size());
                Point direction = unusedNeighbors.get(rnd);
                int diffX = (direction.x - current.x) / 2;
                int diffY = (direction.y - current.y) / 2;
                array[current.y + diffY][current.x + diffX] = true;
                stack.push(direction);
            } else {
                if (bestScore < stack.size()) {
                    bestScore = stack.size();
                    start = current;
                }
                stack.pop();
            }
        }
    }

    public boolean isIntersection(int x, int y) {
        return isUsedCell(x, y);
    }

    public boolean canPlayerMove(int x, int y) {
        return array[y][x];
    }

    // Проверка, не стена ли клетка, и есть ли к ней доступ (вокруг неё не все стены)
    private boolean isUsedCell(int x, int y) {
        if (x < 0 || y < 0 || x >= size - 1 || y >= size - 1) {
            return true;
        }
        return array[y - 1][x] //left
                || array[y][x - 1] //top
                || array[y + 1][x] //right
                || array[y][x + 1]; //bottom
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {

        float cellSize = (float) (rect.right - rect.left) / size;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (!array[i][j]) {
                    float left = j * cellSize + rect.left;
                    float top = i * cellSize + rect.top;
                    canvas.drawRect(left, top, left + cellSize, top + cellSize, wallPaint);
                }
            }
        }
    }
}
