package go;

/**
 * Basic game element.
 *
 */
public class Stone {

    public Chain chain;
    public GameBoard.State state;
    public int liberties;
    // Row and col are need to remove (set to null) this go.Stone from go.Grid
    public int row;
    public int col;

    public Stone(int row, int col, GameBoard.State state) {
        chain = null;
        this.state = state;
        this.row = row;
        this.col = col;
        if((col==GameBoard.SIZE-1 || col==0) && (row==0 || row == GameBoard.SIZE-1))
            liberties=2;
        else if(col==GameBoard.SIZE-1 || row == GameBoard.SIZE-1 || col==0 || row == 0)
            liberties=3;
        else
            liberties=4;
    }
}