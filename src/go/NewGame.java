package go;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class NewGame extends JDialog {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public NewGame(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.setSize(330, 250);
        this.setResizable(false);
        getContentPane().setLayout(null);

        JLabel lblNewGameCreator = new JLabel("New Game Creator");
        lblNewGameCreator.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewGameCreator.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewGameCreator.setBounds(10, 11, 303, 46);
        getContentPane().add(lblNewGameCreator);

        JSpinner spinner = new JSpinner();
        spinner.setFont(new Font("Tahoma", Font.BOLD, 15));
        spinner.setModel(new SpinnerListModel(new String[] {"9", "13", "19"}));
        spinner.setBounds(10, 93, 119, 41);
        getContentPane().add(spinner);

        JLabel lblSetGameboardSize = new JLabel("Set GameBoard Size");
        lblSetGameboardSize.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSetGameboardSize.setBounds(10, 68, 131, 14);
        getContentPane().add(lblSetGameboardSize);

        JLabel lblSetMode = new JLabel("Set Game Mode");
        lblSetMode.setHorizontalAlignment(SwingConstants.CENTER);
        lblSetMode.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSetMode.setBounds(182, 68, 131, 14);
        getContentPane().add(lblSetMode);

        JSpinner spinner_1 = new JSpinner();
        spinner_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        spinner_1.setModel(new SpinnerListModel(new String[] {"Single Player", "Multiplayer"}));
        spinner_1.setBounds(182, 93, 131, 41);
        getContentPane().add(spinner_1);

        JButton btnCreate = new JButton("Create");
        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int gridSize = Integer.parseInt((String)spinner.getValue());
                String mode = (String)spinner_1.getValue();
                out.println(String.valueOf(gridSize) + " " + mode);
                try {
                    Game game = new Game(gridSize, mode, serverAddress, port);
                    game.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Incorrect Server Address");
                }
                dispose();
            }
        });
        btnCreate.setBounds(10, 188, 92, 23);
        getContentPane().add(btnCreate);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("CANCEL");
                dispose();
            }
        });
        btnCancel.setBounds(221, 188, 92, 23);
        getContentPane().add(btnCancel);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
