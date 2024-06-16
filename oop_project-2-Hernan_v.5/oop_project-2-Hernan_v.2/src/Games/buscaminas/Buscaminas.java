package Games.buscaminas;

import Games.GameFunction;
import Games.Iterator;
import statistics.Stat;
import statistics.PlayerScore;
import statistics.StatisticsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class Buscaminas extends JInternalFrame implements GameFunction {
    private Stat stats;
    private JButton[][] buttons;
    private boolean[][] mines;
    private int rows = 10;
    private int cols = 10;
    private int totalMines = 20;
    private boolean gameOver = false;
    private JLabel scoreLabel;
    private StatisticsManager statisticsManager;

    // Implementación del patrón Singleton: Esta variable estática contiene la única instancia de la clase Buscaminas.
    private static Buscaminas instance;

    // Constructor privado para el patrón Singleton
    private Buscaminas() {
        super("Buscaminas", true, true, true, true);
        setSize(400, 400);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        stats = new Stat("score", "player", 0);
        scoreLabel = new JLabel("Score: 0");
        add(scoreLabel, BorderLayout.NORTH);

        buttons = new JButton[rows][cols];
        mines = new boolean[rows][cols];
        setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, cols));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                gamePanel.add(buttons[i][j]);
            }
        }

        add(gamePanel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(restartButton);
        add(controlPanel, BorderLayout.SOUTH);

        statisticsManager = new StatisticsManager("buscaminas");
        initGame();
    }

    // Implementación del patrón Singleton: Método estático para obtener la única instancia de la clase.
    public static synchronized Buscaminas getInstance() {
        if (instance == null) {
            instance = new Buscaminas();
        }
        return instance;
    }

    // Método estático para que GameLoader pueda obtener la instancia Singleton.
    public static Buscaminas loadInstance() {
        return getInstance();
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
        });
    }

    @Override
    public Stat getStats() {
        return stats;
    }

    @Override
    public void reset() {
        stats.setValor(0);
        initGame();
    }

    private void updateScore(int points) {
        stats.incrementScore(points);
        scoreLabel.setText("Score: " + stats.getValor());
    }

    private void initGame() {
        gameOver = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null);
                mines[i][j] = false;
            }
        }
        placeMines();
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            if (!mines[row][col]) {
                mines[row][col] = true;
                minesPlaced++;
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int r = row + i;
                int c = col + j;
                if (r >= 0 && r < rows && c >= 0 && c < cols && mines[r][c]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void revealCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || !buttons[row][col].isEnabled()) {
            return;
        }

        buttons[row][col].setEnabled(false);

        if (mines[row][col]) {
            buttons[row][col].setText("M");
            buttons[row][col].setBackground(Color.RED);
            endGame();
        } else {
            int adjacentMines = countAdjacentMines(row, col);
            buttons[row][col].setText(String.valueOf(adjacentMines));
            if (adjacentMines == 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        revealCell(row + i, col + j);
                    }
                }
            }
        }
    }

    private void endGame() {
        gameOver = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mines[i][j]) {
                    buttons[i][j].setText("M");
                    buttons[i][j].setBackground(Color.RED);
                }
                buttons[i][j].setEnabled(false);
            }
        }
        String playerName = JOptionPane.showInputDialog(this, "Game Over! Ingresa tu nombre:");
        if (playerName != null && !playerName.isEmpty()) {
            statisticsManager.registerScore(playerName, stats.getValor());
            JOptionPane.showMessageDialog(this, "Puntuación registrada: " + stats.getValor());
        }
    }

    public void showStatistics() {
        List<PlayerScore> topScores = statisticsManager.getTopScores();
        StringBuilder statsMessage = new StringBuilder("Top 3 Scores:\n");
        for (PlayerScore score : topScores) {
            statsMessage.append(score.getName()).append(": ").append(score.getScore()).append("\n");
        }
        JOptionPane.showMessageDialog(this, statsMessage.toString());
    }

    // Implementación del patrón Iterador: Este método devuelve un iterador para recorrer los botones del tablero.
    public Iterator getIterator() {
        return new BoardIterator(buttons);
    }

    private class ButtonClickListener implements ActionListener {
        private int x;
        private int y;

        public ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameOver) {
                revealCell(x, y);
                updateScore(1);
            }
        }
    }
}
