package State;

/**
 * Created by DELL on 2016-12-22.
 */
import java.io.IOException;

import go.*;

/**
 * PlayerState Pattern
 */
public interface GameState {
    GameState perform (GameServer server) throws IOException;
}
