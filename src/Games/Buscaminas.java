package Games;

import statistics.Stat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Buscaminas extends JInternalFrame implements GameFunction {
    private Stat stats;
    private JButton[][] buttons;
    private boolean[][] mines;
    private int rows = 10;
    private int cols = 10;
    private int totalMines = 20;
    private boolean gameOver = false;

    public Buscaminas() {
        setTitle("Buscaminas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        stats = new Stat("score", "player", 0);

        buttons = new JButton[rows][cols];
        mines = new boolean[rows][cols];
        setLayout(new GridLayout(rows, cols));
    }

    /*
    Para los otros juegos, el metodo start se debe de ver algo similar a lo de abajo
    el jframe se tiene que inicializar y despues se comienza el juego, en ese orden
     */
    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Buscaminas");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(this);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            initializeButtons();
            placeMines();
        });
    }


    private void initializeButtons() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }
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
            gameOver();
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

    private void gameOver() {
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
        JOptionPane.showMessageDialog(this, "Game Over!");
    }



    @Override
    public Stat getStats() {
        return stats;
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
                stats.setValor(stats.getValor() + 1); // Update stats
            }
        }
    }
}
