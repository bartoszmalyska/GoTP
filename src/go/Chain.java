package go;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A collection of adjacent go.Stone(s).
 *
 */
public class Chain {

    public CopyOnWriteArrayList<Stone> stones;
    public GameBoard.State state;

    public Chain(GameBoard.State state) {
        stones = new CopyOnWriteArrayList<>();
    }

    public int getLiberties() {
        int total = 0;
        for (Stone stone : stones) {
            total += stone.liberties;
        }
        return total;
    }

    public void addStone(Stone stone) {
        stone.chain = this;
        stones.add(stone);
    }

    public void join(Chain chain) {
        for (Stone stone :  chain.stones) {
            addStone(stone);
        }
    }


}