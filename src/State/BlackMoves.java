package State;

import java.io.IOException;

import go.*;

public class BlackMoves implements GameState {

    @Override
    public GameState perform(GameServer server) throws IOException{

        server.sendToBlack("MOVE");

        String msg = server.listenBlack();

        if(msg.contains("PASS")){
            return States.BLACK_PASS.getStateBehavior();
        }
        else if(msg.contains("MOVE")){

            int x=0, y=0;
            String sxy[] = msg.split("\\s+");
            System.out.println(sxy[0]);

            try
            {
                x = Integer.parseInt(sxy[1]);
                y = Integer.parseInt(sxy[2]);
            }
            catch(NumberFormatException ex){
                ex.printStackTrace();
                return States.BLACK_MOVE.getStateBehavior();
            }
            if(server.board.isValid(x,y, GameBoard.State.BLACK)){
                server.sendToBlack("ADDSTONE BLACK " + x + " " + y);
                server.sendToWhite("ADDSTONE BLACK " + x + " " + y);
            }
            else {
                return States.BLACK_MOVE.getStateBehavior();
            }
            return States.WHITE_MOVE.getStateBehavior();
        }
        return States.BLACK_MOVE.getStateBehavior();
    }

}
