package model.tetromino;

import java.awt.*;
import java.util.Random;

public class TetrominoFactory {
    private static final Random random = new Random();
    private static Color[] pallete = {Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.BLUE, Color.YELLOW, Color.RED};
    private static int prevTetromino = 0;
    private static int colorIndex = 0;

    public static Tetromino createRandomTetromino() {
        int type = random.nextInt(7);
        if (type == prevTetromino)
            type = (type + 1) % 7;
        prevTetromino = type;
        int rotation = random.nextInt(4);
        colorIndex += 1;
        Color color = pallete[colorIndex % pallete.length];
        return switch (type) {
            case 0 -> new ITetromino(rotation, color);
            case 1 -> new JTetromino(rotation, color);
            case 2 -> new LTetromino(rotation, color);
            case 3 -> new OTetromino(rotation, color);
            case 4 -> new STetromino(rotation, color);
            case 5 -> new TTetromino(rotation, color);
            case 6 -> new ZTetromino(rotation, color);
            default -> throw new IllegalArgumentException();
        };
    }
}
