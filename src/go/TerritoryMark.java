package go;


public class TerritoryMark {
    public static Territory territory;
    public GameBoard.State state;
    // Row and col are need to remove (set to null) this go.Stone from go.Grid
    public int row;
    public int col;

    public TerritoryMark(int row, int col, GameBoard.State state){
        this.state=state;
        this.row=row;
        this.col=col;
    }
}
