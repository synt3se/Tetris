package model;

import utils.ScoreEntry;

import java.util.*;
import java.io.*;

public class ScoreTable {
    private static final int MAX_ENTRIES = 10;
    private static final String SCORES_FILE = "highscores.dat";
    private static ScoreTable instance;
    private final List<ScoreEntry> highScores;

    private ScoreTable() {
        highScores = new ArrayList<>();
        loadScores();
    }

    public static ScoreTable getInstance() {
        if (instance == null) {
            instance = new ScoreTable();
        }
        return instance;
    }

    public void addScore(String playerName, int score) {
        highScores.add(new ScoreEntry(playerName, score));
        Collections.sort(highScores);

        if (highScores.size() > MAX_ENTRIES) {
            highScores.subList(MAX_ENTRIES, highScores.size()).clear();
        }

        saveScores();
    }

    public List<ScoreEntry> getHighScores() {
        return Collections.unmodifiableList(highScores);
    }

    private void loadScores() {
        File file = new File(SCORES_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                highScores.add(ScoreEntry.fromString(line));
            }
            Collections.sort(highScores);
        } catch (IOException e) {
            System.err.println("Error loading high scores: " + e.getMessage());
        }
    }

    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE))) {
            for (ScoreEntry entry : highScores) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }
}
