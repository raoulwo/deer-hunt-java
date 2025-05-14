package dev.raoulwo;

import dev.raoulwo.audio.AudioManager;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.Player;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.resource.Resource;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;
import dev.raoulwo.util.PixelCoordinate;
import dev.raoulwo.util.Time;

import java.awt.image.BufferedImage;

/**
 * Contains the core game loop.
 */
public class Game implements Runnable {

    private final Thread thread = new Thread(this);
    private final Window window;

    private final World world = new World();

    private final Entity player = Entity.createPlayer(Player.GREEN.name().toLowerCase());

    private final AudioManager audio = AudioManager.instance();

    public Game() {
        // We pass the draw method as a callback to the window.
        this.window = new Window(this::draw, new WindowOptions());

        PixelCoordinate pixel = Tile.tileToPixelCoordinate(0, 0);
        player.x = pixel.x();
        player.y = pixel.y();
    }

    /**
     * Start the game.
     */
    public void start() {
        thread.start();
        audio.playMusic(audio.fightMusic);
    }

    /**
     * Contains the core game loop.
     */
    @Override
    public void run() {
        double previous = System.currentTimeMillis();
        double lag = 0L; // Specifies how many nanoseconds since last frame have passed.

        while (thread.isAlive()) {
            double current = System.currentTimeMillis();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            Time.updateDeltaTime(elapsed);

            // If enough frames worth of nanoseconds have elapsed, update the game.
            while (lag >= Time.TARGET_MILLISECONDS_PER_FRAME) {
                // Update game information at a fixed frame rate.
                update();
                // Render the game graphics.
                window.repaint();
                lag -= Time.TARGET_MILLISECONDS_PER_FRAME;
            }
        }
    }

    /**
     * Is called once per frame from within the core game loop, contains the game logic.
     */
    private void update() {
        player.update(world);
    }

    /**
     * A callback that contains any graphics we want to render for our game. Should not be called manually.
     *
     * @param g A utility graphics object.
     */
    private void draw(Graphics g) {
        for (int row = 0; row < World.MAX_LEVEL_ROWS; row++) {
            for (int col = 0; col < World.MAX_LEVEL_COLUMNS; col++) {
                g.drawTile(world.floor[col][row].sprite, col, row);
            }
        }

        for (int row = 0; row < World.MAX_LEVEL_ROWS; row++) {
            for (int col = 0; col < World.MAX_LEVEL_COLUMNS; col++) {
                Obstacle obstacle = world.obstacles[col][row];
                if (obstacle != null) {
                    g.drawObstacle(obstacle, col, row);
                }
            }
        }

        player.graphics.draw(player, g);

    }

}

