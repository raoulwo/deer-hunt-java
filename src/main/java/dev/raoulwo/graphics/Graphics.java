package dev.raoulwo.graphics;

import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.util.PixelCoordinate;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Graphics {
    public static final int TARGET_RESOLUTION_WIDTH = 480;
    public static final int TARGET_RESOLUTION_HEIGHT = 270;

    // Factor by which we'll scale the tiles.
    public static final int SCALE = 3;

    // Original tile size in pixels.
    public static final int ORIGINAL_TILE_SIZE = 16;
    // Scaled tile size in pixels calculated by original tile size and scale factor.
    public static final int SCALED_TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    // Maximum number of vertical/horizontal tiles on the screen.
    public static final int MAX_SCREEN_COLUMNS = TARGET_RESOLUTION_WIDTH / ORIGINAL_TILE_SIZE;
    public static final int MAX_SCREEN_ROWS = TARGET_RESOLUTION_HEIGHT / ORIGINAL_TILE_SIZE;

    private final Graphics2D g;

    public Graphics(final Graphics2D g) {
        this.g = g;
    }

    public void drawTile(BufferedImage image, int x, int y) {
        PixelCoordinate pixel = Tile.tileToPixelCoordinate(x, y);
        g.drawImage(image, pixel.x(), pixel.y(), Graphics.SCALED_TILE_SIZE, Graphics.SCALED_TILE_SIZE, null);
    }

    public void drawSprite(BufferedImage image, int x, int y) {
        g.drawImage(image, x, y, Graphics.SCALED_TILE_SIZE, Graphics.SCALED_TILE_SIZE, null);
    }

    public void drawObstacle(Obstacle obstacle, int x, int y) {
        if (x == obstacle.coordinate.x() && y == obstacle.coordinate.y()) {
            PixelCoordinate pixel = Tile.tileToPixelCoordinate(x, y);
            g.drawImage(obstacle.tile.sprite, pixel.x(), pixel.y(), obstacle.width * SCALED_TILE_SIZE, obstacle.height * SCALED_TILE_SIZE, null);
        }
    }

    public void drawRect(Color color, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRect( x, y, width, height);
    }
}

// TODO: Move this to a game world class.

// Maximum number of tiles in the world, chosen arbitrarily for now.
//    public final int maxWorldColumns = 44;
//    public final int maxWorldRows = 32;

//    public final int worldWidth = SCALED_TILE_SIZE * maxWorldColumns;
//    public final int worldHeight = SCALED_TILE_SIZE * maxWorldRows;
