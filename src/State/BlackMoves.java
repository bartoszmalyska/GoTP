package State;

import java.io.IOException;

import go.*;

public class BlackMoves implements GameState {
    Stone[][] checkBoard = new Stone[19][19];

    @Override
    public GameState perform(GameServer server) throws IOException{

        server.sendToBlack("MOVE");

        String msg = server.listenBlack();
        for(int row=0;row<19;row++)
        {
            for(int col=0;col<19;col++)
                checkBoard[row][col]=server.board.grid.stones[row][col];
        }

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
                checkMove(server.board.grid.stones,server,x,y);
            }
            else {
                return States.BLACK_MOVE.getStateBehavior();
            }
            return States.WHITE_MOVE.getStateBehavior();
        }
        return States.BLACK_MOVE.getStateBehavior();
    }
    public void checkMove(Stone[][] board,GameServer server, int x,int y)
    {
        for(int row=0;row<19;row++)
        {
            for(int col=0;col<19;col++)
            {
                if(checkBoard[row][col]!=board[row][col] && !(row==x && col==y))
                {
                    System.out.println(row + " " + col);
                    server.sendToBlack("REMOVESTONE WHITE " + row + " " + col);
                    server.sendToWhite("REMOVESTONE WHITE " + row + " " + col);}
            }

        }


    }

}
