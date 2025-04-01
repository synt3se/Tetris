package model.tetromino;

import java.awt.*;

public class ZTetromino extends Tetromino {
    ZTetromino(int rotation, Color newColor) {
        super(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        }, rotation, newColor);
    }
}
