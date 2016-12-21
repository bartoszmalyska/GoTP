package go;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//TODO trzeba dopisać w odpowiednie komunikaty z serwera
public class Board extends JPanel {

    //private static final long serialVersionUID = -494530433694385328L;//
    /**
     * Number of rows/columns.
     */
    /**
     * Number of tiles in row/column. (Size - 1)
     */
    private static int size;
    private static Color state;
    public static int n_of_tiles;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;
    private Point[][] stones;
    private Point[][] marks;
    private Point lastMove;
    public Board(int size,Color state)
    {
        this.setFocusable(true);
        this.setBackground(Color.ORANGE);
        this.size=size;
        this.state=state;
        n_of_tiles=size-1;
        stones=new Point[size][size];

        this.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseReleased(MouseEvent e) {
            // Converts to float for float division and then rounds to
            // provide nearest intersection.
            int row = Math.round((float) (e.getY() - BORDER_SIZE)
                    / TILE_SIZE);
            int col = Math.round((float) (e.getX() - BORDER_SIZE)
                    / TILE_SIZE);
            /*
            out.println(row);
            out.println(col);
             */

            if (row >= size || col >= size || row < 0 || col < 0) {
                return;
            }

            //if (response.startsWith("VALID_MOVE") {
                stones[row][col]=new Point(row,col);
                lastMove=new Point(col,row);
                repaint();
            //}

            //if (response.startsWith("VALID_MARK"){
                marks[row][col]=new Point(row,col);
                lastMove=new Point(col,row);
                repaint();

            //}
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
                }
            }
        }
                /*else if (marks[row][col]!=null) {
                        g2.fillRect(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE, row * TILE_SIZE + BORDER_SIZE - TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }*/
        //}
        //}
        // Highlight last move
        if (lastMove!=null) {
            g2.setColor(Color.RED);
            g2.drawOval(lastMove.x * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                    lastMove.y * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                    TILE_SIZE, TILE_SIZE);

            /*else if(lastMove!=null)
                g2.drawRect(lastMove.x * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                        lastMove.y * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                        TILE_SIZE, TILE_SIZE);*/
        }
    }
        //}
    //}
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(n_of_tiles * TILE_SIZE + BORDER_SIZE * 2,
                n_of_tiles * TILE_SIZE + BORDER_SIZE * 2);
    }


}