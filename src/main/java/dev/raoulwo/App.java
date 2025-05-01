package dev.raoulwo;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); // Disable resizing for simplicity.
        window.setTitle("Deer Hunt");

        // To render our game, we first need to add our game panel to the window.
        GamePanel panel = new GamePanel();
        window.add(panel);
        // Resize the window to fit the size of its subcomponents. In our case, it'll fit the game panel.
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        panel.startGameThread();
    }
}
