package go;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Board extends JPanel {

    /**
     * Number of rows/columns.
     */
    /**
     * Number of tiles in row/column. (Size - 1)
     */
    private Color state=Color.BLACK;
    private static int size;
    public static int n_of_tiles;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;
    private Point[][] stones;
    private Point[][] marks;
    private Point lastMove;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    public Board(int size, String serverAddress, int port) throws IOException
    {
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.setFocusable(true);
        this.setBackground(Color.ORANGE);
        this.size=size;
        n_of_tiles=size-1;
        stones=new Point[size][size];

        this.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseReleased(MouseEvent e) {
            String response= null;
            try {
                response = in.readLine();
            } catch (IOException e1) {
                System.out.println("Nie czyta");
            }
            // Converts to float for float division and then rounds to
            // provide nearest intersection.
            int row = Math.round((float) (e.getY() - BORDER_SIZE)
                    / TILE_SIZE);
            int col = Math.round((float) (e.getX() - BORDER_SIZE)
                    / TILE_SIZE);



            if (row >= size || col >= size || row < 0 || col < 0) {
                return;
            }
            out.println("AddStone" + row + " " + col); // lub AddMark, ale nie wiem jak rozgraniczyć

            if (response.startsWith("VALID_MOVE")){
                stones[row][col]=new Point(row,col);
                lastMove=new Point(col,row);
                repaint();
                out.println("SWITCH");
                if(response.startsWith("BLACK"))
                    state=Color.BLACK;
                else if(response.startsWith("WHITE"))
                    state=Color.WHITE;
            }

            if (response.startsWith("VALID_MARK")){
                marks[row][col]=new Point(row,col);
                lastMove=new Point(col,row);
                repaint();
                out.println("SWITCH");
                if(response.startsWith("BLACK"))
                    state=Color.BLACK;
                else if(response.startsWith("WHITE"))
                    state=Color.WHITE;

            }
        }
    });

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
        g2.setColor(state);
        // Iterate over intersections
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (stones[row][col] != null) {
                    g2.fillOval(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                            row * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                            TILE_SIZE, TILE_SIZE);
                } else if (marks[row][col] != null) {
                    g2.fillRect(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE, row * TILE_SIZE + BORDER_SIZE - TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
        // Highlight last move
        if (lastMove!=null) {
            g2.setColor(Color.RED);
            g2.drawOval(lastMove.x * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                    lastMove.y * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                    TILE_SIZE, TILE_SIZE);
        }
        else if(lastMove!=null /* i territory mode */)
            g2.drawRect(lastMove.x * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                        lastMove.y * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                        TILE_SIZE, TILE_SIZE);
        }
        //}
    //}
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(n_of_tiles * TILE_SIZE + BORDER_SIZE * 2,
                n_of_tiles * TILE_SIZE + BORDER_SIZE * 2);
    }


}