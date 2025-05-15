package dev.raoulwo;

import dev.raoulwo.entity.Entity;
import dev.raoulwo.resource.Resource;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;

import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class World {
    public static final int MAX_LEVEL_COLUMNS = 80;
    public static final int MAX_LEVEL_ROWS = 48;

    public Tile[][] floor = new Tile[MAX_LEVEL_COLUMNS][MAX_LEVEL_ROWS];
    public Obstacle[][] obstacles = new Obstacle[MAX_LEVEL_COLUMNS][MAX_LEVEL_ROWS];
    public ConcurrentMap<String, Entity> entities = new ConcurrentHashMap<>();

    World() {
        try {
            BufferedImage floorSprite = Resource.loadSprite("/sprites/tiles/floor/", "floor_12.png");
            Tile floorTile = new Tile(floorSprite);

            BufferedImage obstacleSprite = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_01.png");
            Tile obstacleTile = new Tile(obstacleSprite, true);
//            Obstacle obstacle = new Obstacle(obstacleTile, new TileCoordinate(14, 4));

            BufferedImage bigObstacleSprite = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_04.png");
            Tile bigObstacleTile = new Tile(bigObstacleSprite, true);
//            Obstacle bigObstacle = new Obstacle(bigObstacleTile, new TileCoordinate(18, 10), 2, 2);

            for (int row = 0; row < MAX_LEVEL_ROWS; row++) {
                for (int col = 0; col < MAX_LEVEL_COLUMNS; col++) {
                    floor[col][row] = floorTile;
                }
            }

            for (int y = 0; y < MAX_LEVEL_ROWS; y += 2) {
                for (int x = 0; x < 16; x += 2) {
                    Obstacle bigObstacle = new Obstacle(bigObstacleTile, new TileCoordinate(x, y), 2, 2);
                    addObstacle(bigObstacle);
                }
            }

            for (int x = MAX_LEVEL_COLUMNS - 2; x > (MAX_LEVEL_COLUMNS - 1) - 16; x -= 2) {
                for (int y = 0; y < MAX_LEVEL_ROWS; y += 2) {
                    Obstacle bigObstacle = new Obstacle(bigObstacleTile, new TileCoordinate(x, y), 2, 2);
                    addObstacle(bigObstacle);
                }
            }

            for (int y = 0; y < 8; y += 2) {
                for (int x = 0; x < MAX_LEVEL_COLUMNS; x += 2) {
                    Obstacle bigObstacle = new Obstacle(bigObstacleTile, new TileCoordinate(x, y), 2, 2);
                    addObstacle(bigObstacle);
                }
            }

            for (int y = MAX_LEVEL_ROWS - 2; y > (MAX_LEVEL_ROWS - 1) - 8; y -= 2) {
                for (int x = 0; x < MAX_LEVEL_COLUMNS; x += 2) {
                    Obstacle bigObstacle = new Obstacle(bigObstacleTile, new TileCoordinate(x, y), 2, 2);
                    addObstacle(bigObstacle);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

    public Entity getEntity(TileCoordinate tile) {
        int x = tile.x();
        int y = tile.y();

        for (Entity entity : entities.values()) {
            TileCoordinate entityTile = Tile.pixelToTileCoordinate(entity.x, entity.y);
            if (entityTile.x() == x && entityTile.y() == y) {
                return entity;
            }
        }

        return null;
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.name);
    }
}
