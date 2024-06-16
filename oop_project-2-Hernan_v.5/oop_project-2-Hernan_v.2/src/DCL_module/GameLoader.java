package DCL_module;

import Games.GameFunction;

public class GameLoader {

    public static GameFunction loadGame(String className) {
        try {
            // Attempt to load the class from the "Games" package
            return loadClass("Games." + className);
        } catch (ClassNotFoundException e) {
            //ELIMINAR ESTE PRINT DE ABAJO
            try {
                // Attempt to load the class from a package with the lowercase class name
                String lowercasePackageName = "Games." + className.toLowerCase();
                String fullClassName = lowercasePackageName + "." + className;
                return loadClass(fullClassName);
            } catch (ClassNotFoundException e2) {
                System.out.println("Class not found in lowercase package: " + className.toLowerCase());
                return null;
            } catch (Exception e2) {
                System.out.println("Error loading or instantiating class from lowercase package: " + e2.getMessage());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error loading or instantiating class: " + e.getMessage());
            return null;
        }
    }

    private static GameFunction loadClass(String fullClassName) throws Exception {
        // Load the specified class
        Class<?> loadedClass = Class.forName(fullClassName);

        // Check if the loaded class implements the GameFunction interface
        if (GameFunction.class.isAssignableFrom(loadedClass)) {
            // Invoke the static "loadInstance" method to get the singleton instance
            return (GameFunction) loadedClass.getMethod("loadInstance").invoke(null);
        } else {
            throw new ClassCastException("The loaded class does not implement the GameFunction interface.");
        }
    }
}

