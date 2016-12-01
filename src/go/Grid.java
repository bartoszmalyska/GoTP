package go;

/**
 * Provides game logic.
 *
 *
 */
/*
 *TODO : Do naprawienia błąd podczas łączenia w chain, wyrzuca NullPointerException. Problem z liczeniem oddechów jeśli kamień jest otoczony kamieniami tego samego koloru
 *
 */
public class Grid {

    private final int SIZE;
    /**
     * [row][column]
     */
    private Stone[][] stones;
    public int counter;


    public Grid(int size) {
        SIZE = size;
        stones = new Stone[SIZE][SIZE];
    }

    /**
     * Adds go.Stone to go.Grid.
     *
     * @param row
     * @param col
     *
     */
    public void addStone(int row, int col, GameBoard.State state) {
        Stone newStone = new Stone(row, col, state);
        stones[row][col] = newStone;
        counter = 0;
        // Check neighbors
        Stone[] neighbors = new Stone[4];
        // Don't check outside the board
        if (row > 0) {
            neighbors[0] = stones[row - 1][col];
        }
        else
            counter++;
        if (row < SIZE - 1) {
            neighbors[1] = stones[row + 1][col];
        }
        else
            counter++;
        if (col > 0) {
            neighbors[2] = stones[row][col - 1];
        }
        else
            counter++;
        if (col < SIZE - 1) {
            neighbors[3] = stones[row][col + 1];
        }
        else
            counter++;
        // Prepare go.Chain for this new go.Stone
        Chain finalChain = new Chain(newStone.state);
        for (Stone neighbor : neighbors) {
            // Do nothing if no adjacent go.Stone
            if (neighbor == null) {
                continue;
            }

            newStone.liberties--;
            neighbor.liberties--;
            System.out.println("Kamień na pozycji " + neighbor.row + "," + neighbor.col + " ma " + neighbor.liberties + " oddechów.");

            // If it's different color than newStone check him
            if (neighbor.state != newStone.state) {
                checkStone(neighbor);
                counter++;
                continue;
            }
            if (neighbor.chain != null) {
                finalChain.join(neighbor.chain);
            }
        }
                finalChain.addStone(newStone);
                if(counter==4)
                {
                    checkStone(newStone);
                }
    }

    /**
     * Check liberties of go.Stone
     *
     * @param stone
     */
    public void checkStone(Stone stone) {
        // Every go.Stone is part of a go.Chain so we check total liberties

        if (stone.chain.getLiberties() == 0) {
            for (Stone s : stone.chain.stones) {
                Stone[] neighbors = new Stone[4];
                // Don't check outside the board
                if (s.row > 0) {
                    neighbors[0] = stones[s.row - 1][s.col];
                }
                if (s.row < SIZE - 1) {
                    neighbors[1] = stones[s.row + 1][s.col];
                }
                if (s.col > 0) {
                    neighbors[2] = stones[s.row][s.col - 1];
                }
                if (s.col < SIZE - 1) {
                    neighbors[3] = stones[s.row][s.col + 1];
                }
                for (Stone neighbor : neighbors) {
                    // Do nothing if no adjacent go.Stone
                    if (neighbor == null) {
                        continue;
                    }
                    neighbor.liberties++;
                    System.out.println("Kamień na pozycji " + neighbor.row + "," + neighbor.col + " ma " + neighbor.liberties + " oddechów.");
                }
                s.chain = null;
                stones[s.row][s.col] = null;
            }
        }
    }

    /**
     * Returns true if given position is occupied by any stone
     *
     * @param row
     * @param col
     * @return true if given position is occupied
     */
    public boolean isOccupied(int row, int col) {
        return stones[row][col] != null;
    }

    /**
     * Returns State (black/white) of given position or null if it's unoccupied.
     * Needs valid row and column.
     *
     * @param row
     * @param col
     * @return
     */
    public GameBoard.State getState(int row, int col) {
        Stone stone = stones[row][col];
        if (stone == null) {
            return null;
        } else {
            // System.out.println("getState != null");
            return stone.state;
        }
    }
}