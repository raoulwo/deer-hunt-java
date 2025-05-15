package dev.raoulwo;

import dev.raoulwo.audio.AudioManager;
import dev.raoulwo.entity.Direction;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.Player;
import dev.raoulwo.entity.State;
import dev.raoulwo.graphics.Camera;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;
import dev.raoulwo.util.PixelCoordinate;
import dev.raoulwo.util.Time;

/**
 * Contains the core game loop.
 */
public class Game implements Runnable {

    private final Thread thread = new Thread(this);
    private final Window window;

    private final World world = new World();

    private final AudioManager audio = AudioManager.instance();

    public Game() {
        // We pass the draw method as a callback to the window.
        this.window = new Window(this::draw, new WindowOptions());

        PixelCoordinate playerPosition = Tile.tileToPixelCoordinate(18, 14);
        Entity player = Entity.createPlayer(Player.GREEN.name().toLowerCase(), true);
        player.x = playerPosition.x();
        player.y = playerPosition.y();
        player.direction = Direction.DOWN;
        world.entities.put(player.name, player);

        PixelCoordinate nonPlayerPosition = Tile.tileToPixelCoordinate(20, 10);
        Entity npc = Entity.createNonPlayer(Player.RED.name().toLowerCase());
        npc.x = nonPlayerPosition.x();
        npc.y = nonPlayerPosition.y();
        world.entities.put(npc.name, npc);

        PixelCoordinate nonPlayerPosition2 = Tile.tileToPixelCoordinate(25, 15);
        Entity npc2 = Entity.createNonPlayer(Player.BLUE.name().toLowerCase());
        npc2.x = nonPlayerPosition2.x();
        npc2.y = nonPlayerPosition2.y();
        world.entities.put(npc2.name, npc2);


//        Entity projectile1 = Entity.createProjectile("projectile1", new TileCoordinate(5, 14), Direction.LEFT);
        Entity projectile1 = Entity.createProjectile("projectile1", new TileCoordinate(35, 14), Direction.RIGHT);
        world.entities.put(projectile1.name, projectile1);

        Entity projectile2 = Entity.createProjectile("projectile2", new TileCoordinate(30, 0), Direction.DOWN);
        world.entities.put(projectile2.name, projectile2);

        Entity projectile3 = Entity.createProjectile("projectile3", new TileCoordinate(24, 11), Direction.LEFT);
        world.entities.put(projectile3.name, projectile3);

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
        for (var entity : world.entities.values()) {
            entity.update(world);
        }
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

        for (var entity : world.entities.values()) {
            entity.graphics.draw(entity, g);
        }
    }

}

