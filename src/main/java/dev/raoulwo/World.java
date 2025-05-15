package dev.raoulwo;

import dev.raoulwo.entity.Entity;
import dev.raoulwo.resource.Resource;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class World {
    public static final int MAX_LEVEL_COLUMNS = 30;
    public static final int MAX_LEVEL_ROWS = 20;

    public Tile[][] floor = new Tile[MAX_LEVEL_COLUMNS][MAX_LEVEL_ROWS];
    public Obstacle[][] obstacles = new Obstacle[MAX_LEVEL_COLUMNS][MAX_LEVEL_ROWS];
    public List<Entity> entities = new ArrayList<>();

    World() {
        try {
            BufferedImage floorSprite = Resource.loadSprite("/sprites/tiles/floor/", "floor_12.png");
            Tile floorTile = new Tile(floorSprite);

            BufferedImage obstacleSprite = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_01.png");
            Tile obstacleTile = new Tile(obstacleSprite, true);
            Obstacle obstacle = new Obstacle(obstacleTile, new TileCoordinate(10, 4));

            BufferedImage bigObstacleSprite = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_04.png");
            Tile bigObstacleTile = new Tile(bigObstacleSprite, true);
            Obstacle bigObstacle = new Obstacle(bigObstacleTile, new TileCoordinate(18, 10), 2, 2);

            for (int row = 0; row < MAX_LEVEL_ROWS; row++) {
                for (int col = 0; col < MAX_LEVEL_COLUMNS; col++) {
                    floor[col][row] = floorTile;
                }
            }

            addObstacle(obstacle);
            addObstacle(bigObstacle);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addObstacle(Obstacle obstacle) {
        int tileX = obstacle.coordinate.x();
        int tileY = obstacle.coordinate.y();
        int width = obstacle.width;
        int height = obstacle.height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                obstacles[tileX + x][tileY + y] = obstacle;
            }
        }
    }

    public Entity getEntity(TileCoordinate tile) {
        int x = tile.x();
        int y = tile.y();

        for (Entity entity : entities) {
            TileCoordinate entityTile = Tile.pixelToTileCoordinate(entity.x, entity.y);
            if (entityTile.x() == x && entityTile.y() == y) {
                return entity;
            }
        }

        return null;
    }
}
