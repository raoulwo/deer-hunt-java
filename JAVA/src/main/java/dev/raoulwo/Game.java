package dev.raoulwo;

import dev.raoulwo.audio.AudioManager;
import dev.raoulwo.entity.Direction;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.Player;
import dev.raoulwo.graphics.Camera;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.ui.UserInterface;
import dev.raoulwo.util.PixelCoordinate;
import dev.raoulwo.util.Time;

import java.util.Comparator;

/**
 * Contains the core game loop.
 */
public class Game implements Runnable {

    /**
     * The game duration in seconds.
     */
    public static final int GAME_TIME = 60;

    private final Thread thread = new Thread(this);
    private final Window window;

    private final World world = new World();

    private final AudioManager audio = AudioManager.instance();
    private final UserInterface ui = UserInterface.instance();

    private boolean gameStarted = false;

    public long startTime;
    public long currentTime;
    public int timeElapsedSeconds;

    public Game() {
        // We pass the draw method as a callback to the window.
        this.window = new Window(this::draw, new WindowOptions());
    }

    /**
     * Start the game thread.
     */
    public void startThread() {
        thread.start();
        audio.playMusic(audio.fightMusic);
        centerOnMiddle();
    }

    private void centerOnMiddle() {
        Camera.instance().centerOn(1920, 1152);
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
        if (gameStarted) {
            for (var entity : world.entities.values()) {
                entity.update(world);
            }
            currentTime = System.currentTimeMillis();
            timeElapsedSeconds = (int)(currentTime - startTime) / 1000;

            if (GAME_TIME - timeElapsedSeconds < 0) {
                stopGame();
            }
        }
        ui.update(this);
    }

    /**
     * A callback that contains any graphics we want to render for our game. Should not be called manually,
     * since it is called from within the game loop.
     *
     * @param g A utility graphics object.
     */
    private void draw(Graphics g) {
        g.drawImageRelativeToCamera(world.floorImage, 0, 0, World.MAX_LEVEL_COLUMNS * Graphics.SCALED_TILE_SIZE, World.MAX_LEVEL_ROWS * Graphics.SCALED_TILE_SIZE);

        for (int row = 0; row < World.MAX_LEVEL_ROWS; row++) {
            for (int col = 0; col < World.MAX_LEVEL_COLUMNS; col++) {
                Obstacle obstacle = world.obstacles[col][row];
                if (obstacle != null) {
                    g.drawObstacle(obstacle, col, row);
                }
            }
        }

        if (gameStarted) {
            for (var entity : world.entities.values()) {
                entity.graphics.draw(entity, g);
            }
        }

        ui.draw(g);
    }

    /**
     * Starts the game from the main menu.
     */
    public void startGame() {
        gameStarted = true;
        startTime = System.currentTimeMillis();
        currentTime = startTime;

        ui.menu.title = "DEER HUNT";

        PixelCoordinate playerPosition = Tile.tileToPixelCoordinate(16 + 8, 8 + 6);
        Entity player = Entity.createPlayer(Player.GREEN.name().toLowerCase(), true);
        player.x = playerPosition.x();
        player.y = playerPosition.y();
        player.direction = Direction.DOWN;
        world.entities.put(player.name, player);
        ui.createPlayerOverlay(player);

        PixelCoordinate nonPlayerPosition = Tile.tileToPixelCoordinate(63 - 8, 8 + 6);
        Entity npc = Entity.createNonPlayer(Player.RED.name().toLowerCase());
        npc.x = nonPlayerPosition.x();
        npc.y = nonPlayerPosition.y();
        world.entities.put(npc.name, npc);
        ui.createPlayerOverlay(npc);

        PixelCoordinate nonPlayerPosition2 = Tile.tileToPixelCoordinate(16 + 8, 39 - 6);
        Entity npc2 = Entity.createNonPlayer(Player.BLUE.name().toLowerCase());
        npc2.x = nonPlayerPosition2.x();
        npc2.y = nonPlayerPosition2.y();
        world.entities.put(npc2.name, npc2);
        ui.createPlayerOverlay(npc2);

        PixelCoordinate nonPlayerPosition3 = Tile.tileToPixelCoordinate(63 - 8, 39 - 6);
        Entity npc3 = Entity.createNonPlayer(Player.YELLOW.name().toLowerCase());
        npc3.x = nonPlayerPosition3.x();
        npc3.y = nonPlayerPosition3.y();
        world.entities.put(npc3.name, npc3);
        ui.createPlayerOverlay(npc3);

        PixelCoordinate monkeyPosition1 = Tile.tileToPixelCoordinate(37, 21);
        Entity monkey1 = Entity.createMonkey("monkey_1");
        monkey1.x = monkeyPosition1.x();
        monkey1.y = monkeyPosition1.y();
        world.entities.put(monkey1.name, monkey1);

        PixelCoordinate monkeyPosition2 = Tile.tileToPixelCoordinate(40, 20);
        Entity monkey2 = Entity.createMonkey("monkey_2");
        monkey2.x = monkeyPosition2.x();
        monkey2.y = monkeyPosition2.y();
        world.entities.put(monkey2.name, monkey2);

        PixelCoordinate monkeyPosition3 = Tile.tileToPixelCoordinate(43, 21);
        Entity monkey3 = Entity.createMonkey("monkey_3");
        monkey3.x = monkeyPosition3.x();
        monkey3.y = monkeyPosition3.y();
        world.entities.put(monkey3.name, monkey3);

        PixelCoordinate monkeyPosition4 = Tile.tileToPixelCoordinate(44, 24);
        Entity monkey4 = Entity.createMonkey("monkey_4");
        monkey4.x = monkeyPosition4.x();
        monkey4.y = monkeyPosition4.y();
        world.entities.put(monkey4.name, monkey4);

        PixelCoordinate monkeyPosition5 = Tile.tileToPixelCoordinate(43, 27);
        Entity monkey5 = Entity.createMonkey("monkey_5");
        monkey5.x = monkeyPosition5.x();
        monkey5.y = monkeyPosition5.y();
        world.entities.put(monkey5.name, monkey5);

        PixelCoordinate monkeyPosition6 = Tile.tileToPixelCoordinate(40, 28);
        Entity monkey6 = Entity.createMonkey("monkey_6");
        monkey6.x = monkeyPosition6.x();
        monkey6.y = monkeyPosition6.y();
        world.entities.put(monkey6.name, monkey6);

        PixelCoordinate monkeyPosition7 = Tile.tileToPixelCoordinate(37, 27);
        Entity monkey7 = Entity.createMonkey("monkey_7");
        monkey7.x = monkeyPosition7.x();
        monkey7.y = monkeyPosition7.y();
        world.entities.put(monkey7.name, monkey7);

        PixelCoordinate monkeyPosition8 = Tile.tileToPixelCoordinate(36, 24);
        Entity monkey8 = Entity.createMonkey("monkey_8");
        monkey8.x = monkeyPosition8.x();
        monkey8.y = monkeyPosition8.y();
        world.entities.put(monkey8.name, monkey8);
    }

    /**
     * Stops the game, resets the game entities and displays the menu including the winner.
     */
    private void stopGame() {
        gameStarted = false;

        Entity winner = world.entities.values().stream().max(Comparator.comparingInt(Entity::getScore)).orElse(null);
        if (winner == null) {
            ui.menu.title = "NO WINNER!";
        } else if (winner.getScore() == 0) {
            ui.menu.title = "NO WINNER!";
        } else {
            ui.menu.title = winner.name.toUpperCase() + " WINS!";
        }

        world.entities.clear();
        centerOnMiddle();

        ui.menu.setActive(true);
    }
}

