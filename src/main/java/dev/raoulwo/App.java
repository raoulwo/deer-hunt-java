package dev.raoulwo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class App {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); // Disable resizing for simplicity.
        window.setTitle("Deer Hunt");

        // To render our game, we first need to add our game panel to the window.
        GamePanel game = new GamePanel();
        window.add(game);

        HostPanel host = new HostPanel(window);
        host.setBorder(new EmptyBorder(5, 5, 5, 5));
//        window.add(host);

        // Resize the window to fit the size of its subcomponents. In our case, it'll fit the game panel.
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

//        host.run();

        game.startGameThread();
    }
}
