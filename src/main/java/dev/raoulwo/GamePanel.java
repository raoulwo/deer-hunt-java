package dev.raoulwo;

import dev.raoulwo.entity.Player;
import dev.raoulwo.item.Heart;
import dev.raoulwo.item.Item;
import dev.raoulwo.tile.TileManager;

import javax.swing.*;
import java.awt.*;

/**
 * The game panel is the component used for rendering our game's graphical content.
 */
public class GamePanel extends JPanel implements Runnable {
    // Original tile size in pixels.
    final int originalTileSize = 16;

    // Factor by which we'll scale the tiles.
    final int scale = 3;

    // Scaled tile size in pixels calculated by original tile size and scale factor.
    public final int scaledTileSize = originalTileSize * scale;

    // Maximum number of vertical/horizontal tiles on the screen (by default 4x3 ratio).
    public final int maxScreenColumns = 16;
    public final int maxScreenRows = 12;

    // The actual screen dimensions calculated by scaled tile size and number of tiles.
    public final int screenWidth = scaledTileSize * maxScreenColumns;
    public final int screenHeight = scaledTileSize * maxScreenRows;

    // Maximum number of tiles in the world, chosen arbitrarily for now.
    public final int maxWorldColumns = 44;
    public final int maxWorldRows = 32;

    public final int worldWidth = scaledTileSize * maxWorldColumns;
    public final int worldHeight = scaledTileSize * maxWorldRows;

    // The target fps our game should be running at.
    final int targetFramesPerSecond = 60;
    final double secondsPerFrame =  1.0 / targetFramesPerSecond;
    final double nanosecondsPerFrame = 1_000_000_000 * secondsPerFrame;

    // The game loop will run in a separate thread.
    Thread gameThread;
    // The key handler will listen for user's key inputs.
    KeyHandler keyHandler = new KeyHandler();

    public Player player = new Player(this, keyHandler);
    public TileManager tileManager = new TileManager(this);
    public CollisionHandler collisionHandler = new CollisionHandler(this);

    public Item item = new Heart(this);

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        // Allows the game panel to be focused in order to receive keyboard inputs.
        setFocusable(true);

        item.worldX = 23 * scaledTileSize;
        item.worldY = 23 * scaledTileSize;
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
        long previous = System.nanoTime();
        double lag = 0L; // Specifies how many nanoseconds since last frame have passed.

        while (gameThread.isAlive()) {
            long current = System.nanoTime();
            long elapsed = current - previous;
            previous = current;
            lag += elapsed;

            // If enough frames worth of nanoseconds have elapsed, update the game.
            while (lag >= nanosecondsPerFrame) {
                // Update game information at a fixed frame rate.
                update();
                lag -= nanosecondsPerFrame;
            }
            // Drawing to the screen usually is independent of game logic, thus it can be done
            // without a fixed time step. If it later on also needs to be fixed, just move it
            // into the loop above.
            repaint();
        }
    }

    /**
     * Contains game logic and updates game information. Will be called each frame.
     */
    public void update() {
        player.update();
    }

    /**
     * Renders the graphical components to the game panel. This method will not be called directly by the game loop
     * each frame, instead the method `repaint` will be called.
     */
    @Override
    public void paintComponent(Graphics g) {
        // Paints the game panel component using a utility `Graphics` object.
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        // Order of drawing determines which sprites are on top or on bottom.
        tileManager.draw(g2d);
        // TODO: Hardcoded an item to render.
        if (!item.pickedUp) {
            item.draw(g2d);
        }
        player.draw(g2d);

        // The graphics object needs to be disposed after use to save some memory.
        g2d.dispose();
    }
}
