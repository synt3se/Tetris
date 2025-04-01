package model.tetromino;

import java.awt.*;

public class ITetromino extends Tetromino {
    ITetromino(int rotation, Color newColor) {
        super(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        }, rotation, newColor);
    }
}
