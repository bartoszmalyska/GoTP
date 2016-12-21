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
                Socket socket = listener.accept();
                System.out.print("dostał");
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                String Query;
                List<String> list = new ArrayList<String>();
                while ((Query = reader.readLine()) != null) {
                    list.add(Query);
                }
                QueryArr = list.toArray(new String[0]);
                if (QueryArr[0] == "NEW") {

                } else if (QueryArr[0] == "JOIN") {
                    //@Todo tu będzie podawanie print do socketa adresu na który ma się połaczyć bo zamknięicu tego połaczenia

                }
            }
        } finally {

        }
    }
    class Player extends Thread{
        GameBoard.State color;
        Player Opponent;
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        public Player(Socket socket, GameBoard.State color){
            this.socket = socket;
            this.color = color;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e){System.out.print("gracz spat");}

        }
    }
}
