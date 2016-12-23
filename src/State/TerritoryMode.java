package State;

import java.io.IOException;

import go.*;

public class TerritoryMode implements GameState {
    TerritoryMark[][] territoryBoard = new TerritoryMark[19][19];

    @Override
    public GameState perform(GameServer context) throws IOException{
        context.sendToBlack("TERRITORYMODE");
        context.sendToWhite("TERRITORYMODE");

        String blackresponse = context.listenBlack();
        String whiteresponse = context.listenWhite();

        for(int row=0;row<19;row++){
            for(int col = 0;col<19;col++)
                territoryBoard[row][col]=context.board.grid.Marks[row][col];

        }
        if(blackresponse.contains("PLACE")){

            int x=0, y=0;
            String bsxy[] = blackresponse.split("\\s+");
            System.out.println(bsxy[0]);

            try{
                x = Integer.parseInt(bsxy[1]);
                y = Integer.parseInt(bsxy[2]);
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
                return States.TERRITORY_MODE.getStateBehavior();
            }
            if (context.board.isValid(x,y, GameBoard.State.BLACK, true)){
                context.sendToBlack(" ADDMARK BLACK " + x + "" + y);
                //checkMark(context.board.grid.Marks,context,x,y);
            }
        }
        if(whiteresponse.contains("PLACE")){

            int x=0, y=0;
            String wsxy[] = whiteresponse.split("\\s+");
            System.out.println(wsxy[0]);

            try{
                x = Integer.parseInt(wsxy[1]);
                y = Integer.parseInt(wsxy[2]);
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
                return States.TERRITORY_MODE.getStateBehavior();
            }
            if (context.board.isValid(x,y, GameBoard.State.WHITE, true)){
                context.sendToWhite(" ADDMARK WHITE " + x + "" + y);
                //checkMark(context.board.grid.Marks,context,x,y);
            }
        }

        return States.TERRITORY_MODE.getStateBehavior();
    }

    /*chyba zbędne? zostawiam narazie
    public void checkMark(TerritoryMark[][] board, GameServer server, int x, int y){
        for(int row=0;row<19;row++){
            for(int col=0;col<19;col++){
                if territoryBoard[row][col]
            }
        }
    }*/

}