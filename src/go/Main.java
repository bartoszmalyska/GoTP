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
        BasicGUI gui = new BasicGUI();
        gui.setVisible(true);
    }
}