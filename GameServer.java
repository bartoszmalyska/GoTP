package go;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import State.*;

public class GameServer extends Thread {
    boolean isGameOver = false;
    public boolean playWithBot = false;
    public BufferedReader blackIs = null;
    public PrintWriter blackOs = null;
    public BufferedReader whiteIs = null;
    public PrintWriter whiteOs = null;

    GameState state = States.BLACK_MOVE.getStateBehavior();
    int size=19;

    private String nameBlack;
    private String nameWhite;

    public GameBoard board;

    public GameServer(GameRoom.SocketHandler player1Handler, GameRoom.SocketHandler player2Handler, String name1, String name2) {
        board = new GameBoard(size);
        blackIs = player1Handler.in;
        blackOs = player1Handler.out;
        whiteIs = player2Handler.in;
        whiteOs = player2Handler.out;

        blackOs.println("BLACK");
        blackOs.flush();
        whiteOs.println("WHITE");
        whiteOs.flush();
        run();
    }

    public boolean pass(){
        // DO OGARNIĘCIA
        return false;
    }
    public void close() {
        try {
            blackIs.close();
            blackOs.close();
            whiteIs.close();
            whiteOs.close();
            isGameOver = true;
            state = States.WIN.getStateBehavior();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.exit(0);
    }


    public String listenWhite() throws IOException{
        String msg=null;
        msg = whiteIs.readLine();
        if(msg.equals("SURRENDER")){
            isGameOver=true;
            close();
            state = States.WIN.getStateBehavior();
        }
        return msg;

    }
    public String listenBlack() throws IOException{
        String msg = blackIs.readLine();
        if(msg.equals("SURRENDER")){
            isGameOver=true;
            close();
            state = States.WIN.getStateBehavior();
        }
        return msg;
    }

    public void sendToWhite(String content){
            whiteOs.println(content);
            whiteOs.flush();
    }
    public void sendToBlack(String content){
        blackOs.println(content);
        blackOs.flush();
    }

    public void run() {

        try {
            while(state.getClass() != WinDialog.class){
                state = state.perform(this);
            }
            if(!isGameOver)
                state = state.perform(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            close();
        }
    }
}