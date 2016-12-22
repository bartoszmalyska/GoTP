package go;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static go.Grid.*;

/**
 * Provides I/O.
 *
 */
public class GameBoard extends JPanel {

    //private static final long serialVersionUID = -494530433694385328L;//
    /**
     * Number of rows/columns.
     */
    /**
     * Number of tiles in row/column. (Size - 1)
     */
    public static int size;

    public static int n_of_tiles;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;

    /**
     * Black/white player/stone
     *
     *
     */
    public enum State {
        BLACK, WHITE
    }

    private State current_player;
    private Grid grid;
    private boolean isSuicide=false;
    private int PassCounter=0;
    boolean territorymode = false;

    public GameBoard(int size) {
        this.size=size;
        n_of_tiles=size-1;
        this.setFocusable(true);
        this.setBackground(Color.ORANGE);
        grid = new Grid(size);

        // Black always starts
        current_player = State.BLACK;

        public boolean isValid(int row, int col) {
                if (row >= size || col >= size || row < 0 || col < 0) {
                    return false;
                }

                if (grid.isOccupied(row, col)) {
                    return false;
                }
                if (territorymode == false) {

                    grid.addStone(row, col, current_player);

                    // Switch current player if move was correct
                    if (grid.counter != 4 || grid.createdKo) {
                        PassCounter = 0;
                        return true;
                    } else
                        return false;
                }

                else if (territorymode == true){
                    if (grid.isMarked(row, col)){
                        return false;
                    }
                    else
                        grid.addMark(row, col, current_player);
                    //lastMove = new Point(col, row);
                    PassCounter = 2;
                    return true;

                }
            }
    public void switchPlayer()
    {
        isSuicide=false;
        if (current_player == State.BLACK) {
            current_player = State.WHITE;
        } else {
            current_player = State.BLACK;
        }
    }
    public void pass() {
        PassCounter++;
        if (PassCounter == 2 && territorymode != true) {
            territorymode = true;
            System.out.println("entering territory mode");
            grid.Automark();
        }
        else if(territorymode == true){
            if(PassCounter == 4){
                //Wywołanie sfokusowanego Windialogu z BlackScore/WhiteScore
            }
        }
        else
            switchPlayer();
    }

}