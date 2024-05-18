package Global_Frame;

import javax.swing.*;
import java.awt.*;
import Games.Buscaminas;
/*
Esta es la clase del frame mayor, dentro de este debe de suceder todo.

 */
public class MainFrame extends JFrame {
    private static JDesktopPane desktopPane;

    public MainFrame() {
        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game Application");
        setVisible(true);
        initGameLibraryFrame();
    }
    //Este metodo de aca abajo pone en el frame mayor todo lo que se le de
    // Pero el juego primero tiene que adaptarse a esto.
    public static void addInternalFrame(JInternalFrame internalFrame) {
        desktopPane.add(internalFrame);
        try {
            internalFrame.setVisible(true);
            internalFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void initGameLibraryFrame() {
        GameLibraryFrame gameLibraryFrame = new GameLibraryFrame();
        gameLibraryFrame.setVisible(true);
        desktopPane.add(gameLibraryFrame);
        try {
            gameLibraryFrame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        GameLibraryFrame.addGame("Snake");
        GameLibraryFrame.addGame("Tetris");

    }
}
