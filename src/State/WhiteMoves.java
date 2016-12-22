package State;

import java.io.IOException;

import go.*;

public class WhiteMoves implements GameState {

    @Override
    public GameState perform(go.GameServer server) throws IOException{

        server.sendToBlack("MOVE");

        String msg = server.listenWhite();

        if(msg.contains("PASS")){
            return States.WHITE_PASS.getStateBehavior();
        }
        else if(msg.contains("MOVE")){

            int x=0, y=0;
            String sxy[] = msg.split("\\s+");
            System.out.println(sxy[0]);

            try{
                x = Integer.parseInt(sxy[1]);
                y = Integer.parseInt(sxy[2]);
            }
            catch(NumberFormatException ex){
                System.out.println("client communication problem");
                return States.WHITE_MOVE.getStateBehavior();
            }
            if(server.board.isValid(x,y, GameBoard.State.WHITE)){
                server.sendToBlack("ADDSTONE WHITE " + x + " " + y);
                server.sendToWhite("ADDSTONE WHITE " + x + " " + y);
            }
            else{
                return States.WHITE_MOVE.getStateBehavior();
            }
            return States.BLACK_MOVE.getStateBehavior();
        }
        return States.WHITE_MOVE.getStateBehavior();
    }

}
