package model;

import model.tetromino.Tetromino;
import model.tetromino.TetrominoFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private ScoreManager scoreManager;
    private GameField gameField;
    private Tetromino currTetromino;
    private Tetromino nextTetromino;
    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;
    private String playerName = "Player";
    private boolean gameOver;
    private List<ModelListener> listeners = new ArrayList<>();

    public GameModel() {
        gameField = new GameField(HEIGHT, WIDTH);
        scoreManager = new ScoreManager();
        initModel();
    }

    public void initModel() {
        gameField.initGrid();
        scoreManager.resetScore();
        gameOver = false;
        currTetromino = TetrominoFactory.createRandomTetromino();
        nextTetromino = TetrominoFactory.createRandomTetromino();
        spawnNewPiece();
    }

    public Tetromino getCurrTetromino() {
        return currTetromino;
    }

    public Tetromino getNextTetromino() {
        return nextTetromino;
    }

    private void spawnNewPiece() {
        currTetromino = nextTetromino;
        Random random = new Random();
        currTetromino.setPosition(random.nextInt(0, WIDTH - 4), -3);
        nextTetromino = TetrominoFactory.createRandomTetromino();
    }

    public void moveDown() {
        currTetromino.move(0, 1);
        if (gameField.checkCollision(currTetromino)) {
            currTetromino.move(0, -1);
            gameField.fixTetromino(currTetromino);
            int linesCleared = gameField.clearLines();
            scoreManager.updateScore(linesCleared);
            checkGameOver();
            if (gameOver)
                return;
            spawnNewPiece();
        }
        notifyListeners();
    }

    public void moveRight() {
        currTetromino.move(1, 0);
        if (gameField.checkCollision(currTetromino)) {
            currTetromino.move(-1, 0);
        }
        notifyListeners();
    }

    public void moveLeft() {
        currTetromino.move(-1, 0);
        if (gameField.checkCollision(currTetromino)) {
            currTetromino.move(1, 0);
        }
        notifyListeners();
    }

    public void rotate() {
        int[][] backupRepr = currTetromino.copyRepr();
        int backupX = currTetromino.getPosX();
        int backupY = currTetromino.getPosY();
        currTetromino.rotateMatrix(currTetromino.getRepr());
        if (gameField.checkCollision(currTetromino)) {
            for (int i = 0; i <= 4; ++i) { // try -2, -1, 1, 2
                currTetromino.setPosition(backupX - 2 + i, backupY);
                if (!gameField.checkCollision(currTetromino))
                    break;
            }
        }
        if (gameField.checkCollision(currTetromino)) {
            currTetromino.setRepr(backupRepr);
            currTetromino.setPosition(backupX, backupY);
        }
        notifyListeners();
    }

    public void drop() {
        Tetromino curr = currTetromino;
        int i = 0;
        while ((curr == currTetromino) && i < 50) {
            moveDown();
            ++i;
        }
        notifyListeners();
    }

    public Tetromino getGhostTetromino() {
        Tetromino ghost = currTetromino.copy();
        while (!gameField.checkCollision(ghost)) {
            ghost.move(0, 1);
        }
        ghost.move(0, -1);
        return ghost;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.playerName = name.trim();
        }
    }

    public int getLevel() {
        return scoreManager.getLevel();
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public int[][] getGrid() {
        return gameField.getGrid();
    }

    public void checkGameOver() {
        if (gameField.isGameOver()) {
            gameOver = true;
            if (!scoreManager.isInTable()) {
                scoreManager.addResultToTable(playerName);
            }
            notifyListeners();
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void addModelListener(ModelListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        listeners.forEach(ModelListener::onModelChanged);
    }
}
