package utils;

public class ScoreEntry implements Comparable<ScoreEntry> {
    private final String playerName;
    private final int score;
    private final long timestamp;

    public ScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        this.timestamp = System.currentTimeMillis();
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(ScoreEntry other) {
        if (this.score != other.score) {
            return Integer.compare(other.score, this.score);
        }
        return Long.compare(this.timestamp, other.timestamp);
    }

    @Override
    public String toString() {
        return playerName + ":" + score + ":" + timestamp;
    }

    public static ScoreEntry fromString(String str) {
        String[] parts = str.split(":");
        return new ScoreEntry(parts[0], Integer.parseInt(parts[1]));
    }
}
