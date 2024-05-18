package Global_Frame;

import Games.GameFunction;
import DCL_module.GameLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
/*
Esta clase es del frame donde se escogen los juegos, al escoger uno se deberia de
saltar a otro frame con su info, ademas si se escoge añadir un juego nuevo
este aparece en la lista y deberia de ser jugable.
CONSIDERACION, EL JUEGO DEBE DE SER .CLASS
 */
public class GameLibraryFrame extends JInternalFrame {
    private static DefaultListModel<String> gameListModel;
    private JList<String> gameList;
    private static ArrayList<GameFunction> lista_de_instancias; //Lista donde se guardan las intancias de los juegos
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

        setLayout(new BorderLayout());
        add(new JScrollPane(gameList), BorderLayout.CENTER);
        add(addGameButton, BorderLayout.SOUTH);

        setSize(300, 400);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public static void addGame(String gameName) {
        gameListModel.addElement(gameName);
    }

    public static ArrayList<GameFunction> getLista_de_instancias() {
        return lista_de_instancias;
    }
}

