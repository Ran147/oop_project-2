package Games.snake;

import Games.GameFunction;
import statistics.PlayerScore;
import statistics.Stat;
import statistics.StatisticsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Snake extends JInternalFrame implements GameFunction {
    private Stat stats;
    private JLabel statusbar;
    private Board gameBoard;
    private StatisticsManager statisticsManager;

    private static Snake instance;

    private Snake() {
        super("Snake", true, true, true, true);
        initUI();
    }

    public static synchronized Snake getInstance() {
        if (instance == null) {
            instance = new Snake();
        }
        return instance;
    }

    public static Snake loadInstance() {
        return getInstance();
    }

    public Snake(boolean forLoader) {
        this();
    }

    private void initUI() {
        statusbar = new JLabel("Score: 0");
        gameBoard = new Board(statusbar, this);

        setLayout(new BorderLayout());
        add(gameBoard, BorderLayout.CENTER);
        add(statusbar, BorderLayout.SOUTH);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(restartButton);
        add(controlPanel, BorderLayout.NORTH);

        setResizable(true);
        pack();

        setLocation(10, 10);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        stats = new Stat("score", "Snake", 0);
        statisticsManager = new StatisticsManager("snake");
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            gameBoard.startGame(); // Start the game explicitly here
            gameBoard.requestFocusInWindow(); // Ensure the panel has focus
        });
    }

    @Override
    public Stat getStats() {
        return stats;
    }

    @Override
    public void reset() {
        stats.setValor(0);
        statusbar.setText("Score: 0");
        gameBoard.reset();
        gameBoard.requestFocusInWindow();
    }

    public void endGame() {
        String playerName = JOptionPane.showInputDialog(this, "Game Over! Ingresa tu nombre:");
        if (playerName != null && !playerName.isEmpty()) {
            int score = Integer.parseInt(statusbar.getText().split(": ")[1]);
            statisticsManager.registerScore(playerName, score);
            JOptionPane.showMessageDialog(this, "Puntuación registrada: " + score);
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
}
