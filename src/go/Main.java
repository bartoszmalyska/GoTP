package go;

import javax.swing.*;
import java.awt.*;

/**
 * Builds UI and starts the game.
 *
 */
public class Main {

    public static final String TITLE = "";
    public static final int BORDER_SIZE = 25;

    public static void main(String[] args) {
        new Main().init();
    }

    private void init() {
        JFrame f = new JFrame();
        f.setTitle(TITLE);

        JPanel container = new JPanel();
        container.setBackground(Color.GRAY);
        container.setLayout(new BorderLayout());
        f.add(container);
        container.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

        GameBoard board = new GameBoard();
        container.add(board);

        f.pack();
        f.setResizable(false);
        f.setLocationByPlatform(true);
        f.setVisible(true);
    }
}