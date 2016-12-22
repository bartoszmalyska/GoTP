
package State;

import java.io.IOException;

import go.*;

public class WinDialog implements GameState {

    @Override
    public GameState perform(GameServer context) throws IOException{
        //wysyłanie punktów
        return States.WIN.getStateBehavior();
    }

}
