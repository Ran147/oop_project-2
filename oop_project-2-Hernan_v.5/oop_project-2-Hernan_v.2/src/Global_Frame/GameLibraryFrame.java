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
    private JButton deleteGameButton;
    private static JDesktopPane desktopPane; // DesktopPane to hold internal frames

    public GameLibraryFrame() {
        super("Game Library", true, true, true, true);
        lista_de_instancias = new ArrayList<>();
        initUI();
        try {
            cargardatosiniciales();
        } catch (GameLoadingException | GameNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initUI() {
        gameListModel = new DefaultListModel<>();
        gameList = new JList<>(gameListModel);
        addGameButton = new JButton("Add Game");
        deleteGameButton = new JButton("Delete Game");
        desktopPane = new JDesktopPane(); // Initialize DesktopPane

        addGameButton.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteGameButton.setFont(new Font("Arial", Font.PLAIN, 14));

        addGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = fileChooser.showOpenDialog(GameLibraryFrame.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String gameName = selectedFile.getName().replace(".class", "");
                    try {
                        GameFunction gameInstance = GameLoader.loadGame(gameName);
                        if (gameInstance != null) {
                            gameListModel.addElement(gameName);
                            lista_de_instancias.add(gameInstance);
                        } else {
                            throw new GameLoadingException("Failed to load game: " + gameName);
                        }
                    } catch (GameLoadingException ex) {
                        JOptionPane.showMessageDialog(GameLibraryFrame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = gameList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedGame = gameList.getSelectedValue();
                    gameListModel.remove(selectedIndex);
                    removeGameInstanceByName(selectedGame);
                    JOptionPane.showMessageDialog(GameLibraryFrame.this, "Game " + selectedGame + " has been deleted.");
                } else {
                    JOptionPane.showMessageDialog(GameLibraryFrame.this, "No game selected for deletion.");
                }
            }
        });

        gameList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && gameList.getSelectedValue() != null) {
                String selectedGame = gameList.getSelectedValue();
                try {
                    GameFunction selectedInstance = getGameInstanceByName(selectedGame);
                    if (selectedInstance != null) {
                        // When a game is selected from the list
                        GameInfoFrame.showGameInfo(selectedGame, selectedInstance);
                    } else {
                        throw new GameNotFoundException("Game not found: " + selectedGame);
                    }
                } catch (GameNotFoundException ex) {
                    JOptionPane.showMessageDialog(GameLibraryFrame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(gameList), BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(addGameButton);
        buttonPanel.add(deleteGameButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add the desktopPane to the center
        add(desktopPane, BorderLayout.CENTER);

        setSize(784, 550);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    //EXCEPCIONES PROPIAS
    private GameFunction getGameInstanceByName(String gameName) throws GameNotFoundException {
        for (GameFunction gameInstance : lista_de_instancias) {
            if (gameInstance.getClass().getSimpleName().equals(gameName)) {
                return gameInstance;
            }
        }
        throw new GameNotFoundException("Game instance not found: " + gameName);
    }
    //AQUI ABAJO EXCEPCIONES PROPIAS
    private void cargardatosiniciales() throws GameLoadingException, GameNotFoundException {
        GameFunction gameInstance1 = GameLoader.loadGame("Buscaminas");
        if (gameInstance1 == null) throw new GameLoadingException("Failed to load game: Buscaminas");
        gameListModel.addElement("Buscaminas");
        lista_de_instancias.add(gameInstance1);

        GameFunction gameInstance2 = GameLoader.loadGame("Snake");
        if (gameInstance2 == null) throw new GameLoadingException("Failed to load game: Snake");
        gameListModel.addElement("Snake");
        lista_de_instancias.add(gameInstance2);

        GameFunction gameInstance3 = GameLoader.loadGame("Tetris");
        if (gameInstance3 == null) throw new GameLoadingException("Failed to load game: Tetris");
        gameListModel.addElement("Tetris");
        lista_de_instancias.add(gameInstance3);
    }

    private void removeGameInstanceByName(String gameName) {
        lista_de_instancias.removeIf(gameInstance -> gameInstance.getClass().getSimpleName().equals(gameName));
    }

    public static void addGame(String gameName) {
        gameListModel.addElement(gameName);
    }

    public static ArrayList<GameFunction> getLista_de_instancias() {
        return lista_de_instancias;
    }

    // Method to add an internal frame to the GameLibraryFrame's desktop pane
    public static void addinternalframe(JInternalFrame internalFrame) {
        desktopPane.add(internalFrame);
        internalFrame.setVisible(true);
    }

    public static class GameInfoFrame extends JInternalFrame {
        private GameFunction gameInstance;
        private JLabel gameInfoLabel;
        private JButton startGameButton;
        private JButton showStatsButton;

        public GameInfoFrame(String gameName, GameFunction gameInstance) {
            super("Opciones de " + gameName, true, true, true, true);
            this.gameInstance = gameInstance;
            initUI();
        }

        private void initUI() {
            gameInfoLabel = new JLabel("Game: " + gameInstance.getClass().getSimpleName());
            startGameButton = new JButton("Start Game");
            showStatsButton = new JButton("Show Statistics");

            startGameButton.setFont(new Font("Arial", Font.PLAIN, 14));
            showStatsButton.setFont(new Font("Arial", Font.PLAIN, 14));

            startGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Use the existing game instance
                    addinternalframe((JInternalFrame) gameInstance);
                    gameInstance.start();
                    dispose();
                }
            });

            showStatsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameInstance instanceof Games.buscaminas.Buscaminas) {
                        ((Games.buscaminas.Buscaminas) gameInstance).showStatistics();
                    } else if (gameInstance instanceof Games.snake.Snake) {
                        ((Games.snake.Snake) gameInstance).showStatistics();
                    } else if (gameInstance instanceof Games.tetris.Tetris) {
                        ((Games.tetris.Tetris) gameInstance).showStatistics();
                    } else {
                        JOptionPane.showMessageDialog(GameInfoFrame.this, "Statistics: " + gameInstance.getStats().toString());
                    }
                }
            });

            setLayout(new BorderLayout());
            add(gameInfoLabel, BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(startGameButton);
            buttonPanel.add(showStatsButton);
            add(buttonPanel, BorderLayout.CENTER);

            setSize(300, 100);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
        }

        public static void showGameInfo(String gameName, GameFunction gameInstance) {
            GameInfoFrame gameInfoFrame = new GameInfoFrame(gameName, gameInstance);
            addinternalframe(gameInfoFrame);
        }
    }
}
