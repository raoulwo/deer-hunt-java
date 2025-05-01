package dev.raoulwo;

import javax.swing.*;
import java.awt.*;

/**
 * The game panel is the component used for rendering our game's graphical content.
 */
public class GamePanel extends JPanel implements Runnable {
    // Original tile size in pixels.
    final int originalTileSize = 64;

    // Factor by which we'll scale the tiles.
    final int scale = 1;

    // Scaled tile size in pixels calculated by original tile size and scale factor.
    final int scaledTileSize = originalTileSize * scale;

    // Maximum number of vertical/horizontal tiles on the screen (by default 4x3 ratio).
    final int maxScreenColumns = 16;
    final int maxScreenRows = 12;

    // The actual screen dimensions calculated by scaled tile size and number of tiles.
    final int screenWidth = scaledTileSize * maxScreenColumns;
    final int screenHeight = scaledTileSize * maxScreenRows;

    // The game loop will run in a separate thread.
    Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    /**
     * Initializes the game thread and then starts it. Should only be called once.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Contains our central game loop.
     */
    @Override
    public void run() {
        while (gameThread.isAlive()) {
            System.out.println("The game loop is running");
        }
    }
}
