package go;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Board extends JPanel {

    public enum StoneColor {
        BLACK,WHITE
    }
    public enum PlayerState {
        ABLE, NOTABLE,TERRITORY
    }
    private static int size=19;
    public static int n_of_tiles=size-1;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;
    private StoneColor [][] stones = new StoneColor[size][size];
    private StoneColor[][] marks = new StoneColor[size][size];
    private boolean territoryMode=false;
    BufferedReader in;
    PrintWriter out;
    Thread receiver;
    PlayerState playerState = PlayerState.NOTABLE;
    StoneColor my;
    public Board(BufferedReader in, PrintWriter out) throws IOException
    {
        this.setFocusable(true);
        this.setBackground(Color.ORANGE);
        this.in=in;
        this.out=out;

        this.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println(playerState);
            if(playerState != PlayerState.NOTABLE)
            {
                int row = Math.round((float) (e.getY() - BORDER_SIZE)
                        / TILE_SIZE);
                int col = Math.round((float) (e.getX() - BORDER_SIZE)
                        / TILE_SIZE);


                if (row >= size || col >= size || row < 0 || col < 0) {
                    return;
                }
                if(territoryMode)
                {
                    //do ogarnięcia
                    repaint();
                    return;
                }
                if(stones[row][col] == StoneColor.BLACK || stones[row][col] == StoneColor.WHITE)
                    return;
                out.println("MOVE " + row + " " + col);
                out.flush();
                repaint();

                if(playerState == PlayerState.ABLE)
                    playerState = PlayerState.NOTABLE;
            }
        }
    });
        receiver = new Thread(new MessageReceiver());
        receiver.start();

}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.BLACK);
        // Draw rows.
        for (int i = 0; i < size; i++) {
            g2.drawLine(BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE, TILE_SIZE
                    * n_of_tiles + BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE);
        }
        // Draw columns.
        for (int i = 0; i < size; i++) {
            g2.drawLine(i * TILE_SIZE + BORDER_SIZE, BORDER_SIZE, i * TILE_SIZE
                    + BORDER_SIZE, TILE_SIZE * n_of_tiles + BORDER_SIZE);
        }
        // Iterate over intersections
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (stones[row][col] != null) {
                    if (stones[row][col] == StoneColor.BLACK)
                        g2.setColor(Color.BLACK);
                    else
                        g2.setColor(Color.WHITE);
                    g2.fillOval(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                            row * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                            TILE_SIZE, TILE_SIZE);
                } else if (marks[row][col] != null) {
                    if (marks[row][col] == StoneColor.BLACK)
                        g2.setColor(Color.BLACK);
                    else
                        g2.setColor(Color.WHITE);
                    g2.fillRect(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE, row * TILE_SIZE + BORDER_SIZE - TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(n_of_tiles * TILE_SIZE + BORDER_SIZE * 2,
                n_of_tiles * TILE_SIZE + BORDER_SIZE * 2);
    }
    public class MessageReceiver implements Runnable {
        public void run() {
            String response;
            try {
                while ((response = in.readLine()) != null) {


                    String[] parts = response.split(("\\s+"));

                    if (response.equals("BLACK")) {
                        my = StoneColor.BLACK;
                    }
                    else if (response.equals("WHITE")) {
                        my = StoneColor.WHITE;
                    }
                    else if (parts[0].equals("ADDSTONE")) {
                        if (parts[1].equals("WHITE")) {
                            stones[Integer.parseInt(parts[2])][Integer.parseInt(parts[3])]=StoneColor.WHITE;
                            repaint();
                        }
                        else if (parts[1].equals("BLACK")) {
                            stones[Integer.parseInt(parts[2])][Integer.parseInt(parts[3])]=StoneColor.BLACK;
                            repaint();
                        }
                    }
                    else if (parts[0].equals("REMOVESTONE")) {
                        if (parts[1].equals("WHITE")) {
                            stones[Integer.parseInt(parts[2])][Integer.parseInt(parts[3])]=null;
                            repaint();
                        }
                        else if (parts[1].equals("BLACK")) {
                            stones[Integer.parseInt(parts[2])][Integer.parseInt(parts[3])]=null;
                            repaint();
                        }
                    }
                    else if (response.equals("MOVE")) {
                        playerState = PlayerState.ABLE;
                    }
                    else if(response.equals("YOU_WON_SURR")){
                        JOptionPane.showConfirmDialog((Component) null, "Opponent left, you won",
                                "alert", JOptionPane.OK_OPTION);
                        close();
                    }


                }
            }
            catch (NumberFormatException ex){
                close();
            }
            catch (Exception ex) {
                JOptionPane.showConfirmDialog((Component) null, "Opponent left, you won",
                        "alert", JOptionPane.OK_OPTION);
                close();
            }
        }
    }

    /**
     * This method closes all of the streams.
     */
    private void close(){
        try {
            out.close();
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.exit(0);
    }


}