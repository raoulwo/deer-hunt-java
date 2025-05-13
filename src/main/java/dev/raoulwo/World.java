package dev.raoulwo;

import dev.raoulwo.resource.Resource;
import dev.raoulwo.tile.Tile;

import java.awt.image.BufferedImage;

public class World {
    public static final int MAX_LEVEL_COLUMNS = 100;
    public static final int MAX_LEVEL_ROWS = 100;

    public Tile[][] floor = new Tile[MAX_LEVEL_COLUMNS][MAX_LEVEL_ROWS];
    public Tile[][] obstacles = new Tile[MAX_LEVEL_COLUMNS][MAX_LEVEL_ROWS];

    World() {
        try {
            BufferedImage floorSprite = Resource.loadSprite("/sprites/tiles/floor/", "floor_01.png");
            Tile floorTile = new Tile(floorSprite);

            BufferedImage obstacleSprite = Resource.loadSprite("/sprites/tiles/obstacles/", "rock_01.png");
            Tile obstacleTile = new Tile(obstacleSprite, true);

            for (int row = 0; row < MAX_LEVEL_ROWS; row++) {
                for (int col = 0; col < MAX_LEVEL_COLUMNS; col++) {
                    floor[col][row] = floorTile;
                }
            }

            obstacles[10][4] = obstacleTile;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
