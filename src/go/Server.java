package go;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Main server
 */
public class Server {

    public static void main(String args[]) {

        Runnable room = new GameRoom();
        new Thread(room).start();
    }
}
class GameRoom extends Thread {
    ServerSocket listener = null;
    private static Map<String, PrintWriter> players = new HashMap<String, PrintWriter> ();
    private static List<SocketHandler> playerThreads;
    public void run() {
        try {
            listener = new ServerSocket(9090);
            playerThreads = new ArrayList<SocketHandler> ();

        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                playerThreads.add(new SocketHandler(listener.accept()));
                playerThreads.get(playerThreads.size()-1).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void challenge(SocketHandler player1Handler, String name1, String name2) {
        SocketHandler player2Handler = null;

        synchronized (playerThreads) {
            for(SocketHandler p : playerThreads){
                if(p.name.equals(name2))
                    player2Handler=p;
            }
        }

        synchronized (players) {
            if(players.containsKey(name1)){
                for(PrintWriter p : players.values()){
                    p.println("PLAYER_LEFT " + name1);
                }
                players.remove(name1);
            }
            if(players.containsKey(name2)){
                for(PrintWriter p : players.values()){
                    p.println("PLAYER_LEFT " + name2);
                }
                players.remove(name2);
            }
        }

        Runnable game = new GameServer(player1Handler, player2Handler, name1, name2);
        new Thread(game).start();



    }
    class SocketHandler extends Thread {
        private Socket socket;
        private String name;
        private String opponentName;
        public BufferedReader in;
        public PrintWriter out;
        private boolean running=true;
        public SocketHandler(Socket s) {
            this.socket = s;
        }
        public void joiningRoom(){
            out.println("WELCOME");
            synchronized (players) {
                for(PrintWriter p : players.values()){
                    p.println("NEW_PLAYER " + name);
                }
                for(String s : players.keySet()){
                    out.println("NEW_PLAYER " + s);
                }
            }
        }
        public void leavingRoom()
        {
            synchronized(players)
            {
                if(players.containsKey(name))
                {
                    for(PrintWriter p : players.values())
                    {
                        p.println("PLAYER_LEFT " + name);
                    }
                    players.remove(name);
                }
            }
        }
        public void run() {
            boolean startedGame = false;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                while (running) {
                    while (running) {
                        out.println("SUBMITNAME");
                        name = in.readLine();
                        if (name == null) {
                            return;
                        }

                        synchronized (players) {
                            if (!players.containsKey(name)) {
                                players.put(name, out);
                                break;
                            }
                        }

                    }

                    joiningRoom();


                    while (!startedGame) {
                        String input = in.readLine();
                        if (input == null) {
                            return;
                        }
                        if (input.contains("PAIR")) {
                            opponentName = input.substring(5);

                            PrintWriter temp = players.getOrDefault(opponentName, null);
                            if (temp != null) {
                                temp.flush();
                                temp.println("CHALLENGE " + name);
                            }
                        } else if (input.contains("START GAME")) {
                            startedGame = true;
                            opponentName = input.substring(11);
                            challenge(this, name, opponentName);
                        } else if (input.contains("GAME STARTED")) {
                            startedGame = true;
                        } else if (input.contains("ACCEPT")) {
                            opponentName = input.substring(7);
                            out.println("START_GAME");
                            startedGame = true;
                            synchronized (players) {
                                PrintWriter temp = players.getOrDefault(opponentName, null);
                                if (temp != null) {
                                    temp.flush();
                                    temp.println("ACCEPTED " + name);
                                }
                            }
                        } else if (input.contains("DECLINE")) {
                            opponentName = input.substring(8);
                            synchronized (players) {
                                PrintWriter temp = players.getOrDefault(opponentName, null);
                                if (temp != null) {
                                    temp.flush();
                                }
                            }
                        }
                    }

                }
            } catch(IOException e2) {
                e2.printStackTrace();
            }
            finally {

                try {
                    if(startedGame==false){
                        leavingRoom();
                        socket.close();
                        in.close();
                        out.close();
                    }
                }
                catch (IOException e) {
                }
            }
        }

    }
}
