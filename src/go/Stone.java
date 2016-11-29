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
        liberties = 4;
        this.row = row;
        this.col = col;
    }
}