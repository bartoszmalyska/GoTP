package go;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;


public class Client {

    Socket socket;
    BufferedReader reader;
    PrintWriter writer;

    boolean isGameStarted=false;
    
    public static void main(String[] args) {

        new Client();
    }
    

    public Client() {
        initialize();
    }
    
    private void initialize() {

        try {
            socket = new Socket("127.0.0.1", 9090);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        OpponentManager opponentManager = new OpponentManager(this, reader, writer);
        opponentManager.frame.setVisible(true);

        while(!isGameStarted){
            System.out.println("Waiting");
        }

        try {
            getUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame(){
        isGameStarted=true; }
    public void getUI() throws IOException {

        JFrame frame = new JFrame();
        frame.setTitle("Gra GO");


        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        frame.add(container);

        Board board = new Board(reader, writer);
        container.add(board);


        JButton buttonPass = new JButton("PASS");
        frame.add(buttonPass,BorderLayout.WEST);
        buttonPass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writer.println("PASS");
                writer.flush();
            }
        });

        JButton buttonSurrender = new JButton("SURRENDER");
        frame.add(buttonSurrender,BorderLayout.EAST);
        buttonSurrender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writer.println("SURRENDER");
                writer.flush();
                try {
                    writer.close();
                    reader.close();
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });

        JButton buttonCommitTerritory = new JButton("COMMIT TERRITORY");
        frame.add(buttonCommitTerritory,BorderLayout.SOUTH);
        buttonCommitTerritory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //zatwierdzenie
            }
        });

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

}