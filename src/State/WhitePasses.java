package State;

import java.io.IOException;

import go.*;

public class WhitePasses implements GameState {

    @Override
    public GameState perform(GameServer server) throws IOException{
        if(server.pass())
            return States.TERRITORY_MODE.getStateBehavior();
        else{
            server.sendToBlack("PASS");
            return States.BLACK_MOVE.getStateBehavior();
        }
    }

}