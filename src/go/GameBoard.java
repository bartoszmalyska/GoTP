package go;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Provides I/O.
 *
 */
public class GameBoard extends JPanel {

    //private static final long serialVersionUID = -494530433694385328L;//
    /**
     * Number of rows/columns.
     */
    /**
     * Number of tiles in row/column. (Size - 1)
     */
    public static int size;

    public static int n_of_tiles;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;

    /**
     * Black/white player/stone
     *
     *
     */
    public enum State {
        BLACK, WHITE
    }

    private State current_player;
    private Grid grid;
    private Point lastMove;
    private boolean isSuicide=false;
    private int PassCounter=0;
    boolean territorymode = false;

    public GameBoard(int size) {
        this.size=size;
        n_of_tiles=size-1;
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setBackground(Color.ORANGE);
        grid = new Grid(size);

        // Black always starts
        current_player = State.BLACK;
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // Converts to float for float division and then rounds to
                // provide nearest intersection.
                int row = Math.round((float) (e.getY() - BORDER_SIZE)
                        / TILE_SIZE);
                int col = Math.round((float) (e.getX() - BORDER_SIZE)
                        / TILE_SIZE);

                // DEBUG INFO
                // System.out.println(String.format("y: %d, x: %d", row, col));

                // Check wherever it's valid
                if (row >= size || col >= size || row < 0 || col < 0) {
                    return;
                }

                if (grid.isOccupied(row, col)) {
                    return;
                }
                if (territorymode == false) {

                    grid.addStone(row, col, current_player);
                    lastMove = new Point(col, row);

                    // Switch current player if move was correct
                    if (grid.counter != 4 || grid.createdKo) {
                        PassCounter = 0;
                        switchPlayer();
                    } else
                        isSuicide = true;
                    repaint();
                }

                else if (territorymode == true){
                    if (grid.isMarked(row, col)){
                        return;
                    }
                    else
                        grid.addMark(row, col, current_player);
                    lastMove = new Point(col, row);
                    PassCounter = 2;
                    switchPlayer();
                    repaint();

                }
            }
        });
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_F1) {
                    PassCounter++;
                    if (PassCounter == 2 && territorymode != true) {
                        territorymode = true;
                        System.out.println("entering territory mode");
                        //Grid.Automark();
                    }
                    else if(territorymode == true){
                        if(PassCounter == 4){
                            //Wywołanie sfokusowanego Windialogu z BlackScore/WhiteScore
                        }
                    }
                    else
                        switchPlayer();
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
        // Iterate over intersections
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid.isOccupied(row, col)) {
                    State state = grid.getState(row, col);
                    if (state != null) {
                        if (state == State.BLACK) {
                            g2.setColor(Color.BLACK);
                        } else {
                            g2.setColor(Color.WHITE);
                        }
                        g2.fillOval(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                                row * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                                TILE_SIZE, TILE_SIZE);
                    }
                } else if (grid.isMarked(row, col)) {
                    State state = grid.getState(row, col);
                    if (state != null) {
                        if (state == State.BLACK) {
                            g2.setColor(Color.BLACK);
                        } else {
                            g2.setColor(Color.WHITE);
                        }
                        g2.fillRect(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE, row * TILE_SIZE + BORDER_SIZE - TILE_SIZE, TILE_SIZE, TILE_SIZE);

                    }
                }
            }
        }
        // Highlight last move
        if (lastMove != null && isSuicide==false) {
            g2.setColor(Color.RED);
            g2.drawOval(lastMove.x * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                    lastMove.y * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                    TILE_SIZE, TILE_SIZE);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(n_of_tiles * TILE_SIZE + BORDER_SIZE * 2,
                n_of_tiles * TILE_SIZE + BORDER_SIZE * 2);
    }
    public void switchPlayer()
    {
        isSuicide=false;
        if (current_player == State.BLACK) {
            current_player = State.WHITE;
        } else {
            current_player = State.BLACK;
        }
    }

}