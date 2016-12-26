package go;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Zaistoteles on 2016-12-23.
 */
public class Bot extends Client {
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;

    boolean isGameStarted=false;

    public static void main(String[] args){

        new Bot();
    }

    public Bot(){
        initialize();
    }

    private void initialize(){

        try{
            socket = new Socket("127.0.0.1", 9090);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        OpponentManager opponentManager = new OpponentManager(this, reader, writer);
        opponentManager.frame.setVisible(false);

        writer.println("BOTE");

        while(!isGameStarted){
            System.out.println("Waiting");
        }

    }
}
