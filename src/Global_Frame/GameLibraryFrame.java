package Global_Frame;

import Games.GameFunction;
import DCL_module.GameLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class GameLibraryFrame extends JInternalFrame {
    private static DefaultListModel<String> gameListModel;
    private JList<String> gameList;
    private static ArrayList<GameFunction> lista_de_instancias; // LISTA DE INSTANCIAS DE LOS JUEGOS
    private JButton addGameButton;

    public GameLibraryFrame() {
        super("Game Library", true, true, true, true);
        lista_de_instancias = new ArrayList<>();
        initUI();
    }

    private void initUI() {
        gameListModel = new DefaultListModel<>();
        gameList = new JList<>(gameListModel);
        addGameButton = new JButton("Add Game");

        addGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = fileChooser.showOpenDialog(GameLibraryFrame.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String gameName = selectedFile.getName().replace(".class", "");
                    GameFunction gameInstance = GameLoader.loadGame(gameName);
                    if (gameInstance != null) {
                        gameListModel.addElement(gameName);
                        lista_de_instancias.add(gameInstance);
                    } else {
                        JOptionPane.showMessageDialog(GameLibraryFrame.this, "Failed to load game: " + gameName);
                    }
                }
            }
        });

        gameList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && gameList.getSelectedValue() != null) {
                String selectedGame = gameList.getSelectedValue();
                GameFunction selectedInstance = getGameInstanceByName(selectedGame);
                if (selectedInstance != null) {
                    GameInfoFrame.showGameInfo(selectedGame, selectedInstance);
                }
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(gameList), BorderLayout.CENTER);
        add(addGameButton, BorderLayout.SOUTH);

        setSize(300, 400);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private GameFunction getGameInstanceByName(String gameName) {
        for (GameFunction gameInstance : lista_de_instancias) {
            if (gameInstance.getClass().getSimpleName().equals(gameName)) {
                return gameInstance;
            }
        }
        return null;
    }

    public static void addGame(String gameName) {
        gameListModel.addElement(gameName);
    }

    public static ArrayList<GameFunction> getLista_de_instancias() {
        return lista_de_instancias;
    }

    public static class GameInfoFrame extends JInternalFrame {
        private GameFunction gameInstance;
        private JLabel gameInfoLabel;
        private JButton startGameButton;
        private JButton showStatsButton;

        public GameInfoFrame(String gameName, GameFunction gameInstance) {
            super("Game Info: " + gameName, true, true, true, true);
            this.gameInstance = gameInstance;
            initUI();
        }

        private void initUI() {
            gameInfoLabel = new JLabel("Game: " + gameInstance.getClass().getSimpleName());
            startGameButton = new JButton("Start Game");
            showStatsButton = new JButton("Show Statistics");

            startGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Use the existing game instance
                    MainFrame.addInternalFrame((JInternalFrame) gameInstance);
                    gameInstance.start();
                }
            });
            /*
            AQUI ES DONDE ESTA EL BOTON DE VER ESTADISTICAS
             */
            showStatsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(GameInfoFrame.this, "Statistics: " + gameInstance.getStats().toString());
                }
            });

            setLayout(new BorderLayout());
            add(gameInfoLabel, BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(startGameButton);
            buttonPanel.add(showStatsButton);
            add(buttonPanel, BorderLayout.CENTER);

            setSize(300, 200);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
        }

        private JInternalFrame createGameFrame(GameFunction gameInstance) {
            JInternalFrame gameFrame = new JInternalFrame(gameInstance.getClass().getSimpleName(), true, true, true, true);
            gameFrame.setLayout(new BorderLayout());
            gameFrame.add((Component) gameInstance, BorderLayout.CENTER); // Ensure gameInstance is a component
            gameFrame.pack();
            gameFrame.setVisible(true);
            return gameFrame;
        }

        public static void showGameInfo(String gameName, GameFunction gameInstance) {
            GameInfoFrame gameInfoFrame = new GameInfoFrame(gameName, gameInstance);
            MainFrame.addInternalFrame(gameInfoFrame);
        }
    }
}
