package go;


import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class Game extends JFrame {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Game(int gridSize, String mode, String serverAddress, int port) throws IOException {

        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        this.setTitle("Go");
        getContentPane().setLayout(new BorderLayout(0, 0));
        JButton btnPass = new JButton("Pass Move");
        getContentPane().add(btnPass, BorderLayout.WEST);
        btnPass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("PASS");

            }
        });

        JButton btnSurrender = new JButton("Surrender");
        getContentPane().add(btnSurrender, BorderLayout.EAST);
        btnSurrender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("SURRENDER");
            }
        });

        JButton btnSaveGame = new JButton("Save Game");
        getContentPane().add(btnSaveGame, BorderLayout.SOUTH);
        btnSaveGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("SAVE");
            }
        });

        JPanel container = new JPanel();
        container.setBackground(Color.GRAY);
        container.setLayout(new BorderLayout());
        getContentPane().add(container, BorderLayout.CENTER);

        GameBoard board = new GameBoard(gridSize);
        container.add(board);

        this.pack();
        this.setResizable(false);



    }
}