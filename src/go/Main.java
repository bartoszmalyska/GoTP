package go;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Tutaj musi zaczynać klienta
 *
 */
public class Main {

    public static void main(String[] args) {
        BasicGUI gui = null;
        try {
            gui = new BasicGUI("localhost:" + args[1]);
        } catch (IOException e) {
            System.out.println("Nie tworzy");
        }
        gui.setVisible(true);
    }
}