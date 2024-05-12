package DCL_module;
// El nombre del paquete es"dynamic class loading" o DCL
//Esta clase se encarga de instanciar automaticamente lo que se quiera
//Por lo que se va a hacer como una clase estatica para que se pueda llamar de todos lados
// si da problemas que esto este en el otro package considerar mandarlo al mismo
import Games.GameFunction;
/*
La clase de abajo lo que hace es que al llamar el metodo estatico "loadgame"
y pasarle el nombre del juego este verifica si el juego implementa la interfaz deseada y lo
instancia, para devolverlo para que se siga usando.
 */
public class GameLoader {
    public static GameFunction loadGame(String className) {
        try {

            Class<?> loadedClass = Class.forName("Games."+className);
            if (GameFunction.class.isAssignableFrom(loadedClass)) {

                return (GameFunction) loadedClass.getDeclaredConstructor().newInstance();
            } else {
                System.out.println("The loaded class does not implement the GameFunction interface.");
                return null;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + className);
            return null;
        } catch (Exception e) {
            System.out.println("Error loading or instantiating class: " + e.getMessage());
            return null;
        }
    }
}

