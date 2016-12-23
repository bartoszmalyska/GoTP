package State;

import java.io.IOException;

import go.*;

public class WhiteMoves implements GameState {
    Stone[][] checkBoard = new Stone[19][19];

    @Override
    public GameState perform(GameServer server) throws IOException{
        server.sendToWhite("MOVE");

        String msg = server.listenWhite();
        for(int row=0;row<19;row++)
        {
            for(int col=0;col<19;col++)
                checkBoard[row][col]=server.board.grid.stones[row][col];
        }

        if(msg.contains("PASS")){
            return States.WHITE_PASS.getStateBehavior();
        }
        else if(msg.contains("MOVE")){
            server.passcounter=0;
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
            if(server.board.isValid(x,y, GameBoard.State.WHITE, false)){
                server.sendToWhite("ADDSTONE WHITE " + x + " " + y);
                server.sendToBlack("ADDSTONE WHITE " + x + " " + y);
                checkMove(server.board.grid.stones,server,x,y);
            }
            else{
                return States.WHITE_MOVE.getStateBehavior();
            }
            return States.BLACK_MOVE.getStateBehavior();
        }
        return States.WHITE_MOVE.getStateBehavior();
    }
    public void checkMove(Stone[][] board,GameServer server, int x,int y)
    {
        for(int row=0;row<19;row++)
        {
            for(int col=0;col<19;col++)
            {
                if(checkBoard[row][col]!=board[row][col] && !(row==x && col==y))
                {
                    server.sendToBlack("REMOVESTONE BLACK " + row + " " + col);
                    server.sendToWhite("REMOVESTONE BLACK " + row + " " + col);
                }
            }

        }


    }

}
