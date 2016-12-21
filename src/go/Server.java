package go;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
Szablon dla QueryArr
0 - NEW lub JOIN
1 - Size gameboarda
2 - z botem czy nie (z botem to single)
3 - adres GameRoom'a
 */

public class Server {
    String[] QueryArr;

    public void handlingserver(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        System.out.print("wstał");
        try {
            while (true) {
                Game game = new  Game();
                Player playerBlack = new Player(listener.accept(), GameBoard.State.BLACK);
                Player playerWhite = new Player(listener.accept(), GameBoard.State.WHITE);
                playerBlack.setOpponent(playerWhite);
                playerWhite.setOpponent(playerBlack);
                playerBlack.start();
                playerWhite.start();
            }
        } finally {
            listener.close();
        }
    }

    class Player extends Thread {
        GameBoard.State color;
        Player opponent;
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        Player currentPlayer;

        public Player(Socket socket, GameBoard.State color) {
            this.socket = socket;
            this.color = color;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.print("gracz spat");
            }

        }

        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }

        public void run() {
            try {
                if (color == GameBoard.State.BLACK) {
                    out.println("Ruch czarnego");
                }
                while (true) {
                    String command = in.readLine();
                    if (command.startsWith("place")) {
                        if () {
                            //tu damy to wykonanie ruchu czyli podpięcie logiki gry
                        } else {
                            out.println("?");
                        }
                    } else if (command.startsWith("Concede")){
                        return;
                    }
                }
            }catch (IOException e){
                System.out.println("Gracz spat bo : " + e);
            } finally {
                try {socket.close();} catch (IOException e){}
                }
            }
        }
    }
