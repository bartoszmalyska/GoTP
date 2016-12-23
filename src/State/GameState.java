package State;

/**
 * Created by DELL on 2016-12-22.
 */
import java.io.IOException;

import go.*;

/**
 * State Pattern
 */
public interface GameState {
    GameState perform (final GameServer server) throws IOException;
}
