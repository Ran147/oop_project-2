package Games;

import Games.GameFunction;
import statistics.Stat;

import javax.swing.*;
import java.awt.*;
//Hacer que implemente jinternalframe
public class Snake extends JInternalFrame implements GameFunction {
    private Stat stats;
    private JLabel statusbar;

    public Snake() {
        super("Snake", true, true, true, true);
        initUI();
    }

    private void initUI() {
        statusbar = new JLabel("Score: 0");
        add(statusbar, BorderLayout.SOUTH);
        add(new Board(statusbar));

        setResizable(true);
        pack();

        setLocation(10, 10);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        stats = new Stat("3", "Snake", 0);
    }


    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            Snake snake = new Snake();
            snake.setVisible(true);
        });
    }

    @Override
    public Stat getStats() {
        return stats;
    }
}
