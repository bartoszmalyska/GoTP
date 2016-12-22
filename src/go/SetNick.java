package go;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.io.*;
import java.net.*;

public class SetNick extends JDialog {

    private JPanel contentPanel = new JPanel();
    public JTextField textField;
    public String mynick;
    public boolean free;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    Thread receiver;

    JLabel statusbar;

    public SetNick(OpponentManager window, BufferedReader reader, PrintWriter writer) {

        this.reader = reader;
        this.writer = writer;

        setBounds(100, 100, 300, 150);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        statusbar = new JLabel("");
        statusbar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        statusbar.setBounds(20, 236, 466, 25);
        getContentPane().add(statusbar,BorderLayout.SOUTH);
        contentPanel.add(statusbar);

        JLabel lblGiveNick = new JLabel("Podaj swoj nick:");
        lblGiveNick.setBounds(85, 11, 143, 30);
        contentPanel.add(lblGiveNick);

        textField = new JTextField();
        textField.setBounds(62, 52, 166, 30);
        contentPanel.add(textField);
        textField.setColumns(10);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mynick=textField.getText();
            }
        });

        JPanel buttonPane = new JPanel();							//buttonPane ()
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        buttonPane.add(okButton);
        //okButton.setActionCommand("OK");
        //getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String textfieldnick = textField.getText();
                String mynick = checkingNick(textfieldnick);

                if (!mynick.equals("failed") && !mynick.equals("Wpisz swoj pseudonim!")) {

                    window.myNick = mynick;
                    writer.println(mynick);
                    writer.flush();
                    setVisible(false);
                }
            }
        });


        JButton cancelButton = new JButton("Anuluj");
        buttonPane.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {	//if user clicks cancel, window closes
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        receiver = new Thread(new MessageReceiver());		//running new thread; it reads messages from input stream
        receiver.start();										//(where are data from server) and prints them
    }

    public String checkingNick(String mynick) {

        //mynick = textField.getText();

        if (!mynick.isEmpty()) {

            free = true;
            try {
                if (free) {		//is approved only if given nick doesn't exists in database

                    System.out.println("Wysylam na serwer nick: " + mynick);
                    setVisible(false);
                    dispose();
                    return mynick;
                }
                else {
                    System.out.println("Wpisany przez Ciebie nick jest juz zajety, podaj inny.");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        else {
            System.out.println("Wpisz swoj pseudonim!");
            return "Wpisz swoj pseudonim!";
        }
        return "failed";
    }


    /**
     * The aim of this class is to read messages from the input stream, to which data from server goes
     * and depending on message content performs different things.
     */
    public class MessageReceiver implements Runnable {
        public void run() {
            String message;
            try {
                while(!free) {		//loop which reads messages sent from server line by line
                    message = reader.readLine();
                    System.out.println("Odczytano : " + message);
                    switch (message) {
                        case "ACCEPTED":
                            System.out.println("Wykonuje dzialania dla ACCEPTED");
                            free = true;
                            break;
                        case "SUBMITNAME":
                            System.out.println("Wykonuje dzialania dla SUBMITNAME");
                            free = false;
                            break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}