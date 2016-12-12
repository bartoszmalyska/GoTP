package go;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Zaistoteles on 2016-12-12.
 */
public class Territory {
    public GameBoard.State state;
    public CopyOnWriteArrayList<TerritoryMark> Marks;
    public Territory(GameBoard.State state){Marks = new CopyOnWriteArrayList<>();}

    public void addMark(TerritoryMark territoryMark){
        TerritoryMark.territory = this;
        Marks.add(territoryMark);
    }

}
