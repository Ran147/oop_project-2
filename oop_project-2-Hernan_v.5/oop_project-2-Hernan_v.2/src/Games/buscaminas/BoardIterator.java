package Games.buscaminas;

import Games.Iterator;

import javax.swing.*;

public class BoardIterator implements Iterator {
    private JButton[][] buttons;
    private int row;
    private int col;

    // Implementación del patrón Iterador: Inicializa el iterador con el tablero de botones.
    public BoardIterator(JButton[][] buttons) {
        this.buttons = buttons;
        this.row = 0;
        this.col = 0;
    }

    // Implementación del patrón Iterador: Verifica si hay más elementos en la colección.
    @Override
    public boolean hasNext() {
        return row < buttons.length && col < buttons[0].length;
    }

    // Implementación del patrón Iterador: Retorna el siguiente elemento en la colección.
    @Override
    public Object next() {
        JButton button = buttons[row][col];
        col++;
        if (col == buttons[0].length) {
            col = 0;
            row++;
        }
        return button;
    }
}
