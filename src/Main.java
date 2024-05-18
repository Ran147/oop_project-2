import DCL_module.GameLoader;
import GUI.MyFrame;
import Games.GameFunction;
import Global_Frame.GameLibraryFrame;
import Global_Frame.MainFrame;

import javax.swing.*;
import java.util.Scanner;
// Buscaminas,snake y tetris pueden ser llamados correctamente
//faltaria hacer un internal frame que muestre los juegos cargados, que estos sean seleccionables
// al seleccionarlos se le debe de abrir otro con su info y posibilidad de jugar
// actualizacion 18/5/2024
// Hay que ver como hacer la pantalla 2 y que esta pase a la pantalla 3, o sea hay que hacer
// nuevo frame con la info del juego y que sea jugable.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();

        });



    }
}
