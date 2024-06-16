package Global_Frame;
//Esta excepcion indicaria cuando hubo un problema al cargar los juegos
public class GameLoadingException extends Exception {
    public GameLoadingException(String message) {
        super(message);
    }

    public GameLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
