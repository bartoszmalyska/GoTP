package go;

/**
 * Created by DELL on 2016-12-22.
 */
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.PrintWriter;


public class OpponentManager implements ListSelectionListener {

    public JFrame frame;
    public JList<String> listofplayers;
    DefaultListModel<String> model = new DefaultListModel<>();
    public String opponent;
    public String myNick;
    public SetNick window;

    SetNick setNick;

    BufferedReader reader;
    PrintWriter writer;
    Thread receiver;
    Client c;

    boolean hasGameStarted =false;

    public OpponentManager(Client cl, BufferedReader r, PrintWriter w) {
        reader =  r;
        writer = w;
        c=cl;

        setNick = new SetNick(this, reader, writer);
        setNick.setVisible(true);
        frame = new JFrame();
        frame.setBounds(100, 100, 482, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);

        listofplayers = new JList<String>(model);

        listofplayers.setBounds(41, 48, 151, 170);

        frame.getContentPane().add(listofplayers);

        listofplayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listofplayers.setLayoutOrientation(JList.VERTICAL);
        listofplayers.addListSelectionListener(this);

        JScrollPane listofplayersscr = new JScrollPane(listofplayers);
        frame.getContentPane().add(listofplayersscr);
        listofplayersscr.setBounds(41, 48, 151, 170);

        JButton play = new JButton("Challenge");
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (opponent != null){
                    String selected = (String) listofplayers.getSelectedValue();
                    writer.println("PAIR " + selected);
                    writer.flush();
                }
            }
        });
        play.setBounds(286, 61, 138, 46);
        frame.getContentPane().add(play);
        receiver = new Thread(new MessageReceiver());
        receiver.start();

        if(hasGameStarted){
            receiver.interrupt();
        }

    }
    @Override
    public void valueChanged(ListSelectionEvent event) {
        if(!event.getValueIsAdjusting()) {
            String selected = (String) listofplayers.getSelectedValue();
            opponent=selected;

        }
    }
    public class MessageReceiver implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null && hasGameStarted ==false) {
                    if(message.contains("NEW_PLAYER") && !myNick.equals(message.substring(11))){
                        model.addElement(message.substring(11));
                    }
                    else if(message.contains("PLAYER_LEFT")){
                        model.removeElement(message.substring(12));
                    }
                    else if(message.contains("CHALLENGE")) {
                        if (JOptionPane.showConfirmDialog(null, "Do you accept challenge from " + message.substring(10) +"?", "",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            writer.println("ACCEPT " + message.substring(10));
                            writer.flush();
                        } else {
                            writer.println("DECLINE " + message.substring(10));
                            writer.flush();
                        }
                    }
                    else if(message.contains("ACCEPTED")){
                        opponent = message.substring(9);
                        writer.println("start game "+opponent);
                        writer.flush();
                        frame.setVisible(false);
                        c.startGame();
                        hasGameStarted =true;

                    }
                    else if(message.equals("START_GAME")){
                        frame.setVisible(false);
                        c.startGame();
                        hasGameStarted =true;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
