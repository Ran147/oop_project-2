import Global_Frame.MainFrame;

// Buscaminas,snake y tetris pueden ser llamados correctamente
//faltaria hacer un internal frame que muestre los juegos cargados, que estos sean seleccionables
// al seleccionarlos se le debe de abrir otro con su info y posibilidad de jugar
// actualizacion 18/5/2024
// Hay que ver como hacer la pantalla 2 y que esta pase a la pantalla 3, o sea hay que hacer
// nuevo frame con la info del juego y que sea jugable.
//Ver logica de cierre de frames y los botones de volver al menu
/*
Actualizacion 18/5/2024 -- 16:59
--Ya corre todos los juegos bien, la pantalla 1, pantalla 2 y 3 sirven correctamente
-- Todavia no se pueden ver estadisticas.
-- Los juegos ocupan opciones para reiniciar juego
-- Implementar patrones
-- Implementar excepciones.
 */
//---------------------------
/*
Actualizacion 20/5/2024 -- 08:38
-- Ver con el profe si le agrado el cambio de la pantalla principal
-- Implementar excepciones
-- Añadir las estadisticas, que sean visibles en cada juego y que muestre los 3 mejores
-- Hacer que estas estadisticas se guarden en un archivo y recargarlas cada vez que se inicie el programa
-- Empaquetar
 */
//-------------
/*
Actualizacion 21/5/2024 -- 14:44
---- Se realizaron cambios en
+++Main
-- se volvio mas pequeña la cantidad de lineas
+++Mainframe
-- Creacion de funcion stylization
+++Gameloader
-- Se puede cargar desde carpetas ahora
+++Gamelibraryframe
-- Se cargan inicialmente los 3 juegos (para dejar un cuarto en disponibilidad de agregar)
 */

public class Main {
    public static void main(String[] args) {
        MainFrame.stylization();
        new MainFrame();

    }
}
