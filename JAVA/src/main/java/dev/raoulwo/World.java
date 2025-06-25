package dev.raoulwo;

import dev.raoulwo.entity.Entity;
import dev.raoulwo.resource.Resource;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;
import dev.raoulwo.util.PixelCoordinate;

import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents the game world including its tiles, obstacles and entities.
 */
public class World {

    /**
     * Maximum number of world columns.
     */
    public static final int MAX_LEVEL_COLUMNS = 80;

    /**
     * Maximum number of world rows.
     */
    public static final int MAX_LEVEL_ROWS = 48;

    public Tile[][] floor = new Tile[MAX_LEVEL_COLUMNS][MAX_LEVEL_ROWS];
    public Obstacle[][] obstacles = new Obstacle[MAX_LEVEL_COLUMNS][MAX_LEVEL_ROWS];
    public ConcurrentMap<String, Entity> entities = new ConcurrentHashMap<>();

    public BufferedImage floorImage;

    World() {
        try {
            floorImage = Resource.loadSprite("/sprites/tiles/", "floor.png");

            BufferedImage rock1Image = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_01.png");
            Tile rock1Tile = new Tile(rock1Image, true);

            BufferedImage rock2Image = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_02.png");
            Tile rock2Tile = new Tile(rock2Image, true);

            BufferedImage rock3Image = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_03.png");
            Tile rock3Tile = new Tile(rock3Image, true);

            BufferedImage rock4Image = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_04.png");
            Tile rock4Tile = new Tile(rock4Image, true);

            BufferedImage rock5Image = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_05.png");
            Tile rock5Tile = new Tile(rock5Image, true);

            BufferedImage rock6Image = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_06.png");
            Tile rock6Tile = new Tile(rock6Image, true);

            BufferedImage rock7Image = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_07.png");
            Tile rock7Tile = new Tile(rock7Image, true);

            BufferedImage stumpImage = Resource.loadSprite("/sprites/tiles/obstacles/", "stump.png");
            Tile stumpTile = new Tile(stumpImage, true);

            BufferedImage treeImage = Resource.loadSprite("/sprites/tiles/obstacles/", "tree.png");
            Tile treeTile = new Tile(treeImage, true);

            addObstacle(new Obstacle(treeTile, new TileCoordinate(39, 23), 3, 3));
            addObstacle(new Obstacle(treeTile, new TileCoordinate(61, 28), 3, 3));
            addObstacle(new Obstacle(treeTile, new TileCoordinate(33, 37), 3, 3));
            addObstacle(new Obstacle(treeTile, new TileCoordinate(44, 8), 3, 3));
            addObstacle(new Obstacle(treeTile, new TileCoordinate(16, 18), 3, 3));

            addObstacle(new Obstacle(stumpTile, new TileCoordinate(36, 17), 1, 1));
            addObstacle(new Obstacle(stumpTile, new TileCoordinate(44, 17), 1, 1));
            addObstacle(new Obstacle(stumpTile, new TileCoordinate(36, 31), 1, 1));
            addObstacle(new Obstacle(stumpTile, new TileCoordinate(44, 31), 1, 1));
            addObstacle(new Obstacle(stumpTile, new TileCoordinate(33, 20), 1, 1));
            addObstacle(new Obstacle(stumpTile, new TileCoordinate(33, 28), 1, 1));
            addObstacle(new Obstacle(stumpTile, new TileCoordinate(47, 20), 1, 1));
            addObstacle(new Obstacle(stumpTile, new TileCoordinate(47, 28), 1, 1));

            addObstacle(new Obstacle(rock7Tile, new TileCoordinate(39, 34), 4, 3));
            addObstacle(new Obstacle(rock7Tile, new TileCoordinate(38, 11), 4, 3));

            addObstacle(new Obstacle(rock6Tile, new TileCoordinate(19, 23), 2, 3));
            addObstacle(new Obstacle(rock6Tile, new TileCoordinate(58, 23), 2, 3));

            for (int y = 0; y < MAX_LEVEL_ROWS; y += 2) {
                for (int x = 0; x < 16; x += 2) {
                    Obstacle bigObstacle = new Obstacle(rock4Tile, new TileCoordinate(x, y), 2, 2);
                    addObstacle(bigObstacle);
                }
            }

            for (int x = MAX_LEVEL_COLUMNS - 2; x > (MAX_LEVEL_COLUMNS - 1) - 16; x -= 2) {
                for (int y = 0; y < MAX_LEVEL_ROWS; y += 2) {
                    Obstacle bigObstacle = new Obstacle(rock4Tile, new TileCoordinate(x, y), 2, 2);
                    addObstacle(bigObstacle);
                }
            }

            for (int y = 0; y < 8; y += 2) {
                for (int x = 0; x < MAX_LEVEL_COLUMNS; x += 2) {
                    Obstacle bigObstacle = new Obstacle(rock4Tile, new TileCoordinate(x, y), 2, 2);
                    addObstacle(bigObstacle);
                }
            }

            for (int y = MAX_LEVEL_ROWS - 2; y > (MAX_LEVEL_ROWS - 1) - 8; y -= 2) {
                for (int x = 0; x < MAX_LEVEL_COLUMNS; x += 2) {
                    Obstacle bigObstacle = new Obstacle(rock4Tile, new TileCoordinate(x, y), 2, 2);
                    addObstacle(bigObstacle);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Used to add an obstacle to the game world.
     *
     * @param obstacle The obstacle to be added.
     */
    public void addObstacle(Obstacle obstacle) {
        int tileX = obstacle.coordinate.x();
        int tileY = obstacle.coordinate.y();
        int width = obstacle.width;
        int height = obstacle.height;

        if (obstacles[tileX][tileY] != null) {
            return;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                obstacles[tileX + x][tileY + y] = obstacle;
            }
        }
    }

    /**
     * Used to fetch an entity at given tile.
     *
     * @param tile The tile coordinate at which to fetch the entity.
     * @return The entity at given tile coordinate, or null if no entity exists at given coordinate.
     */
    public Entity getEntity(TileCoordinate tile) {
        int x = tile.x();
        int y = tile.y();

        return getEntity(x, y);
    }

    /**
     * Used to fetch an entity at given tile.
     *
     * @param x The x tile coordinate.
     * @param y The y tile coordinate.
     * @return The entity at given tile coordinate, or null if no entity exists at given coordinate.
     */
    public Entity getEntity(int x, int y) {
        for (Entity entity : entities.values()) {
            TileCoordinate entityTile = Tile.pixelToTileCoordinate(entity.x, entity.y);
            if (entityTile.x() == x && entityTile.y() == y) {
                return entity;
            }
        }

        return null;
    }

    /**
     * Removes the given entity from the world.
     *
     * @param entity The entity to remove from the game world.
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity.name);
    }

    /**
     * Spawns a monkey at a random unoccupied coordinate.
     */
    public void spawnMonkey() {
        TileCoordinate coordinate = getRandomFreeCoordinate();
        PixelCoordinate pixelCoordinate = Tile.tileToPixelCoordinate(coordinate.x(), coordinate.y());
        Entity monkey = Entity.createMonkey("monkey_" + System.currentTimeMillis());
        monkey.x = pixelCoordinate.x();
        monkey.y = pixelCoordinate.y();
        entities.put(monkey.name, monkey);
    }

    /**
     * Computes a random tile coordinate which is not occupied by an obstacle or entity.
     *
     * @return A randomly chosen unoccupied tile coordinate.
     */
    private TileCoordinate getRandomFreeCoordinate() {
        TileCoordinate result = null;

        do {
            int randomX = (int) (Math.random() * (MAX_LEVEL_COLUMNS - 32) + 16);
            int randomY = (int) (Math.random() * (MAX_LEVEL_ROWS - 16) + 8);
            TileCoordinate coordinate = new TileCoordinate(randomX, randomY);
            if (getEntity(coordinate) != null) {
                continue;
            }
            if (obstacles[coordinate.x()][coordinate.y()] != null) {
                continue;
            }
            result = coordinate;
        } while (result == null);

        return result;
    }
}
