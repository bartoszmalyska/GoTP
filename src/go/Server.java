package go;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/*
Szablon dla QueryArr
0 - NEW lub JOIN
1 - Size gameboarda
2 - z botem czy nie (z botem to single)
3 - adres GameRoom'a
 */

public class Server {

    public void handlingserver(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        System.out.print("wstał");
        BufferedReader input = new BufferedReader(new InputStreamReader(listener.accept().getInputStream()));
        String query = input.readLine();
        try {
            while (true) {
                List<String> querylist = new ArrayList<String>();
                while((query = input.readLine()) != null){querylist.add(query);}
                String[] QueryArr = querylist.toArray(new String[0]);
                if (QueryArr[0] == "NEW") {
                    GameSession game = new GameSession();
                    GameSession.Player playerBlack = game.new Player(listener.accept(), GameBoard.State.BLACK);
                    GameSession.Player playerWhite = game.new Player(listener.accept(), GameBoard.State.WHITE);
                    playerBlack.setOpponent(playerWhite);
                    playerWhite.setOpponent(playerBlack);
                    game.currentPlayer = playerBlack;
                    playerBlack.start();
                    playerWhite.start();
                }
                if (QueryArr[0] == "JOIN"){
                    GameSession game;
                    GameSession.Player playerBlack =
                }
            }
        } finally {
            listener.close();
        }
    }

    class GameSession {
        GameBoard.GameBoard (size);
        //konstruktor gry na serwerze//

        Player currentPlayer;

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
                        if (command.startsWith("Addstone")) {
                            if (isValid(row, col)== true) {
                                Grid.addStone(row, col, color);
                            } else {
                                out.println("?");
                            }
                        } else if (command.startsWith("Concede")) {
                            return;
                        }else if (command.startsWith("Addmark")){
                            if (isValid(row, col)== true){
                                Grid.addMark(rowm, col, color);
                            }

                        }else if (command.startsWith("Pass")){
                            GameBoard.switchPlayer();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Gracz spat bo : " + e);
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
