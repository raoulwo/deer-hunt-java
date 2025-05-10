package dev.raoulwo;

import javax.swing.*;
import java.awt.*;

/**
 * The host panel is where the player hosts a new game or joins an existing one
 */
public class HostPanel extends JPanel implements Runnable {

    private JFrame window;


    public HostPanel(JFrame window) {
        this.window=window;

        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setLayout(new GridBagLayout());
        // Allows the game panel to be focused in order to receive keyboard inputs.
        setFocusable(true);
        setBackground(Color.white);

        GridBagConstraints c = new GridBagConstraints();

        c.ipadx=5;
        c.ipady=5;

        JLabel hostLabel = new JLabel("Host new Game");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        add(hostLabel, c);


        JLabel xLabel = new JLabel("Horizontal size:");
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(xLabel, c);

        JTextField xText = new JTextField("16", 16);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 1;
        add(xText, c);

        JLabel yLabel = new JLabel("Vertical size:");
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(yLabel, c);

        JTextField yText = new JTextField("12", 16);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 2;
        add(yText, c);

        JButton hostButton = new JButton("Create Game");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        add(hostButton, c);


        JLabel joinLabel = new JLabel("Join existing Game");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 4;
        add(joinLabel, c);

        JLabel ipLabel = new JLabel("IP ADress");
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 5;
        add(ipLabel, c);

        JTextField ipText = new JTextField("127.0.0.1:8080", 16);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 5;
        add(ipText, c);

        JButton joinButton = new JButton("Join Game");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 6;
        add(joinButton, c);

        hostButton.addActionListener(e -> hostButtonPressed());
        joinButton.addActionListener(e -> hostButtonPressed());
    }

    @Override
    public void run() {
        System.out.println("Hello");
        //TODO set up ports and queue's
    }

    private void hostButtonPressed(){
        System.out.println("Trying to create a new game");


        // To render our game, we first need to add our game panel to the window.
        GamePanel panel = new GamePanel();
        window.add(panel);
        // Resize the window to fit the size of its subcomponents. In our case, it'll fit the game panel.
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        panel.startGameThread(); //TODO provide a plyer/host variable
    }


    private void JoinButtonPressed(){
        System.out.println("Trying to join a game");

        // To render our game, we first need to add our game panel to the window.
        GamePanel panel = new GamePanel();
        window.add(panel);
        // Resize the window to fit the size of its subcomponents. In our case, it'll fit the game panel.
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        panel.startGameThread(); //TODO provide a plyer/host variable
    }

}
