package go;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BasicGUI extends JFrame {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static int port = 9090;

    public BasicGUI(String serverAddress) throws IOException {
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.setTitle("Go");
        this.setSize(400, 200);
        this.setResizable(false);
        getContentPane().setLayout(null);

        JLabel lblGo = new JLabel("Go 1.0");
        lblGo.setHorizontalAlignment(SwingConstants.CENTER);
        lblGo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblGo.setBounds(138, 0, 102, 61);
        getContentPane().add(lblGo);

        JButton btnNewGame = new JButton("New Game");
        btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                out.println("NEW");
                NewGame newGame = null;
                try {
                    newGame = new NewGame(serverAddress,port);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Incorrect Server Address");
                }
                newGame.setVisible(true);
            }
        });
        btnNewGame.setBounds(10, 69, 102, 49);
        getContentPane().add(btnNewGame);

        JButton btnLoad = new JButton("Load Game");
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("JOIN");
            }
        });
        btnLoad.setBounds(138, 69, 102, 49);
        getContentPane().add(btnLoad);

        JButton btnInfo = new JButton("Info");
        btnInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnInfo.setBounds(272, 69, 102, 49);
        getContentPane().add(btnInfo);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
