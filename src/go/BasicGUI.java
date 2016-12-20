package go;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BasicGUI extends JFrame {
    public BasicGUI() {
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
                NewGame newGame = new NewGame();
                newGame.setVisible(true);
            }
        });
        btnNewGame.setBounds(10, 69, 102, 49);
        getContentPane().add(btnNewGame);

        JButton btnLoad = new JButton("Load Game");
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
