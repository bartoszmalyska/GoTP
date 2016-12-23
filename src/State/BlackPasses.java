package State;

import java.io.IOException;

import go.*;

public class BlackPasses implements GameState {

    @Override
    public GameState perform(GameServer server) throws IOException{
        if(server.pass())
            return States.TERRITORY_MODE.getStateBehavior();
        else{
            server.sendToWhite("PASS");
            return States.WHITE_MOVE.getStateBehavior();
        }
    }

}