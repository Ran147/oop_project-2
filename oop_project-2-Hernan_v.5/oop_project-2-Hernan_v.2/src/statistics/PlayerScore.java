package statistics;

import java.io.Serializable;

public class PlayerScore implements Serializable {
    private static final long serialVersionUID = 1L; // Agregar un serialVersionUID
    private String name;
    private int score;

    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }
}
