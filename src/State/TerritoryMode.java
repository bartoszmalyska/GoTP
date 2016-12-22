package State;

import java.io.IOException;

import go.*;

public class TerritoryMode implements GameState {

    @Override
    public GameState perform(GameServer context) throws IOException{
        //Do rozpisania
        return States.TERRITORY_MODE.getStateBehavior();
    }

}
