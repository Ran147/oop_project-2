package Games;
/*Esta es la interfaz que tiene que implementar todos los juegos.
para mas adelante, el nombre de la clase de cada juego, tiene que ser el nombre del juego
esto para hacer el instanciado automatico un toque más facil.

 */

public interface GameFunction {
    public void start();
    public void getStats();
}
