package statistics;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatisticsManager {
    private List<PlayerScore> topScores;
    private String scoresFile;

    public StatisticsManager(String gameName) {
        this.scoresFile = gameName + "_scores.dat";
        topScores = loadScores();
    }

    public void registerScore(String playerName, int score) {
        topScores.add(new PlayerScore(playerName, score));
        topScores.sort(Comparator.comparingInt(PlayerScore::getScore).reversed());

        if (topScores.size() > 3) {
            topScores = new ArrayList<>(topScores.subList(0, 3));
        }

        saveScores();
    }

    public List<PlayerScore> getTopScores() {
        return topScores;
    }

    private List<PlayerScore> loadScores() {
        List<PlayerScore> scores = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(scoresFile))) {
            scores = (List<PlayerScore>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Archivo no encontrado o error de lectura, se ignora
        }
        return scores;
    }

    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(scoresFile))) {
            oos.writeObject(topScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
