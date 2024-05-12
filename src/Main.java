import DCL_module.GameLoader;
import Games.GameFunction;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Esto de abajo es una simple prueba para ver si efectivamente puedo instanciar
        //Una clase en runtime y si, efectivamente se puede.
        Scanner scan = new Scanner(System.in);
        System.out.println(("Nombre del juego: "));
        String name = scan.nextLine();
        GameFunction call = GameLoader.loadGame(name);
        if(call!=null){
            System.out.println("Siiiii");
        }
        else{
            System.out.println("Nooooooo");
        }


    }
}