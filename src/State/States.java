package State;

/**
 * Created by DELL on 2016-12-22.
 */

public enum States {


    WHITE_MOVE {
        public GameState getStateBehavior() {
            return new WhiteMoves();
        }
    },
    BLACK_MOVE {
        public GameState getStateBehavior() {
            return new BlackMoves();
        }
    },
    WHITE_PASS {
        public GameState getStateBehavior() {
            return new WhitePasses();
        }
    },
    BLACK_PASS {
        public GameState getStateBehavior() {
            return new BlackPasses();
        }
    },
    WIN {
        public GameState getStateBehavior() {
            return new WinDialog();
        }
    },
    TERRITORY_MODE {
        public GameState getStateBehavior() {
            return new TerritoryMode();
        }
    };

    /**
     * Default
     * @return
     */
    public GameState getStateBehavior() {
        return null;
    }

}