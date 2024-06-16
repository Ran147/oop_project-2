package Global_Frame;
//Esta excepcion sale cuando no se encuentra la instancia del juego seleccionado
public class GameNotFoundException extends Exception {
    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
