package go;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static go.Grid.*;

public class GameBoard {

    public static int size;


    /**
     * Black/white player/stone
     *
     *
     */
    public enum State {
        BLACK, WHITE
    }

    public Grid grid;

    public GameBoard(int size) {
        this.size = size;
        grid = new Grid(size);
    }

    public boolean isValid(int row, int col, State state, boolean territorymode) {
        if (row >= size || col >= size || row < 0 || col < 0) {
            return false;
        }

        if (grid.isOccupied(row, col)) {
            return false;
        }
        if (territorymode == false) {

            grid.addStone(row, col, state);

            if (grid.counter != 4 || grid.createdKo) {
                return true;
            } else
                return false;
        }

        else if (territorymode == true){
            if (grid.isMarked(row, col)){
                return false;
            }
            else
                grid.addMark(row, col, state);
            return true;

        }
        return false;
    }

}