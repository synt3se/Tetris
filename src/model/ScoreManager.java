package model;

public class ScoreManager {
    private ScoreTable table;
    private int[] scores = {0, 40, 100, 300, 1200};
    private boolean isWritten = false;
    private int score = 0;
    private int level = 0;
    private int linesToNextLevel = 5;
    private int clearedLines = 0;

    public ScoreManager() {
        table = ScoreTable.getInstance();
    }

    public void addResultToTable(String playerName) {
        isWritten = true;
        table.addScore(playerName, score);
    }

    public boolean isInTable() {
        return isWritten;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    private void updateLevel() {
        if (clearedLines >= linesToNextLevel) {
            level += 1;
            linesToNextLevel = linesToNextLevel + level * 2;
        }
    }

    public void updateScore(int lines) {
        clearedLines += lines;
        updateLevel();
        score += scores[lines] * level;
    }

    public void resetScore() {
        isWritten = false;
        score = 0;
        level = 0;
    }
}
