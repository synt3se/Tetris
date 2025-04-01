package model.tetromino;

import java.awt.*;
import java.util.Arrays;

public class Tetromino {
    int x;
    int y;
    Color color;
    int[][] repr;
    static Color[] pallete = {Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.BLUE, Color.YELLOW, Color.RED};

    Tetromino(int[][] repr, int rotation, Color newColor) {
        setRepr(repr);
        for (int i = 0; i < rotation; ++i) {
            rotateMatrix(repr);
        }
        setColor(newColor);
    }

    public Tetromino copy() {
        Tetromino newTetromino = new Tetromino(repr, 0, color);
        newTetromino.setPosition(x, y);
        return newTetromino;
    }

    public int[][] getRepr() {
        return repr;
    }

    public int[][] copyRepr() {
        return Arrays.stream(repr).map(int[]::clone).toArray(int[][]::new);
    }

    public void setRepr(int[][] newRepr) {
        repr = newRepr;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }

    public int getPosX() {
        return x;
    }

    public int getPosY() {
        return y;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void rotateMatrix(int[][] mat) {
        int n = mat.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; ++j) {
                int temp = mat[i][j];
                mat[i][j] = mat[i][n - 1 - j];
                mat[i][n - 1 - j] = temp;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = temp;
            }
        }
    }
}
