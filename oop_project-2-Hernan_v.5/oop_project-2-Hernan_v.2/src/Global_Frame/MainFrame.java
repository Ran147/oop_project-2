package Global_Frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static JDesktopPane desktopPane;

    public MainFrame() {
        super("GESTOR DE JUEGOS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame on the screen

        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);

        initGameLibraryFrame();

        setVisible(true);
    }

    public static void addInternalFrame(JInternalFrame internalFrame) {
        desktopPane.add(internalFrame);
        internalFrame.setVisible(true);
    }

    private void initGameLibraryFrame() {
        GameLibraryFrame gameLibraryFrame = new GameLibraryFrame();
        addInternalFrame(gameLibraryFrame);
    }
    public static void stylization(){
        try {
            // Usar Nimbus como LookAndFeel para que se vea bonito
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}