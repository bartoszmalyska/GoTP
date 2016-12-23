package go;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.net.*;

public class SetNick extends JDialog {

    private JPanel contentPanel = new JPanel();
    public JTextField textField;
    public String myNick;
    public boolean available;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    Thread receiver;

    public SetNick(OpponentManager window, BufferedReader reader, PrintWriter writer) {

        this.reader = reader;
        this.writer = writer;

        setBounds(100, 100, 300, 150);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNick = new JLabel("Nick:");
        lblNick.setBounds(85, 11, 143, 30);
        contentPanel.add(lblNick);

        textField = new JTextField();
        textField.setBounds(62, 52, 166, 30);
        contentPanel.add(textField);
        textField.setColumns(10);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myNick =textField.getText();
            }
        });

        JPanel buttonPane = new JPanel();							//buttonPane ()
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        buttonPane.add(okButton);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tfNick = textField.getText();
                String mynick = nickCheck(tfNick);

                if (!mynick.equals("failed") && !mynick.equals("Nick!")) {

                    window.myNick = mynick;
                    writer.println(mynick);
                    writer.flush();
                    setVisible(false);
                }
            }
        });


        JButton cancelButton = new JButton("Cancel");
        buttonPane.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {	//if user clicks cancel, window closes
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        receiver = new Thread(new MessageReceiver());		//running new thread; it reads messages from input stream
        receiver.start();//(where are data from server) and prints them

        if(myNick != "Nick!" && myNick != "failed"){
            if (available = true){
                receiver.stop();
            }
        }
    }

    public String nickCheck(String mynick) {

        //myNick = textField.getText();

        if (!mynick.isEmpty()) {

            available = true;
            try {
                if (available) {		//is approved only if given nick doesn't exists in database
                    
                    setVisible(false);
                    dispose();
                    return mynick;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        else {
            return "Nick!";
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
                while(!available) {
                    message = reader.readLine();
                    switch (message) {
                        case "ACCEPTED":
                            available = true;
                            break;
                        case "SUBMITNAME":
                            available = false;
                            break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}