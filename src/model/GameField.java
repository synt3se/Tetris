package model;

import model.tetromino.Tetromino;

import java.util.Arrays;

public class GameField {
    private int[][] grid;
    private int height;
    private int width;

    public GameField(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new int[height][width];
        initGrid();
    }

    public void initGrid() {
        for (int y = 0; y < height; y++) {
            Arrays.fill(grid[y], 0);
        }
    }

    private boolean isCollide(int x, int y) {
        return (x < 0 || x >= width || y >= height);
    }

    public boolean checkCollision(Tetromino tetromino) {
        int x0 = tetromino.getPosX();
        int y0 = tetromino.getPosY();
        int[][] repr = tetromino.getRepr();
        for (int y = 0; y < repr.length; ++y) {
            for (int x = 0; x < repr[0].length; ++x) {
                if (repr[y][x] == 1 &&
                        (isCollide(x0 + x, y0 + y) || (y0 + y >= 0 && grid[y0 + y][x0 + x] == 1))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void fixTetromino(Tetromino tetromino) {
        int x0 = tetromino.getPosX();
        int y0 = tetromino.getPosY();
        int[][] repr = tetromino.getRepr();

        int[][] newGrid = new int[height][width];
        for (int y = 0; y < height; y++) {
            System.arraycopy(grid[y], 0, newGrid[y], 0, width);
        }
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (isCollide(x0 + x, y0 + y) || y0 + y < 0)
                    continue;
                if (repr[y][x] == 1) {
                    newGrid[y0 + y][x0 + x] = 1;
                }
            }
        }
        grid = newGrid;
    }

    private boolean isLineFull(int y) {
        for (int x = 0; x < width; x++) {
            if (grid[y][x] == 0) return false;
        }
        return true;
    }

    private void removeLine(int y) {
        System.arraycopy(grid, 0, grid, 1, y);
        Arrays.fill(grid[0], 0);
    }

    public int clearLines() {
        int linesRemoved = 0;
        for (int y = height - 1; y >= 0; y--) {
            if (isLineFull(y)) {
                removeLine(y);
                linesRemoved++;
                y++; // Check the same position again
            }
        }
        return linesRemoved;
    }

    public int[][] getGrid() {
        return Arrays.copyOf(grid, grid.length);
    }

    public boolean isGameOver() {
        for (int i = 0; i < width; ++ i) {
            if (grid[0][i] == 1)
                return true;
        }
        return false;
    }
}
