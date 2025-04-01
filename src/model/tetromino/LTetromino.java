package model.tetromino;

import java.awt.*;

public class LTetromino extends Tetromino {
    LTetromino(int rotation, Color newColor) {
        super(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        }, rotation, newColor);
    }
}
