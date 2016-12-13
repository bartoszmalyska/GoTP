package go;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Zaistoteles on 2016-12-12.
 */
public class Territory {
    public CopyOnWriteArrayList<TerritoryMark> Marks;
    public Territory(){Marks = new CopyOnWriteArrayList<>();}

    public void addMark(TerritoryMark territoryMark){
        TerritoryMark.territory = this;
        Marks.add(territoryMark);
    }

}
