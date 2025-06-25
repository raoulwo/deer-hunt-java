package dev.raoulwo.tile;

import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.util.PixelCoordinate;

import java.awt.image.BufferedImage;

public class Tile {
    private static final int TILE_SIZE = Graphics.SCALED_TILE_SIZE;

    /**
     * Transforms a pixel coordinate pair into the corresponding tile coordinates.
     *
     * @param pixel The pixel coordinate pair to transform.
     * @return The transformed pixel coordinates.
     */
    public static TileCoordinate pixelToTileCoordinate(PixelCoordinate pixel) {
        return pixelToTileCoordinate(pixel.x(), pixel.y());
    }

    /**
     * Transforms a pixel coordinate pair into the corresponding tile coordinates.
     *
     * @param x The pixel x coordinate.
     * @param y The pixel y coordinate.
     * @return The transformed pixel coordinates.
     */
    public static TileCoordinate pixelToTileCoordinate(int x, int y) {
        int tileX = x / TILE_SIZE;
        int tileY = y / TILE_SIZE;
        return new TileCoordinate(tileX, tileY);
    }

    /**
     * Transforms a tile coordinate pair into the corresponding pixel coordinates (top-left corner).
     *
     * @param tile The tile coordinate pair to transform.
     * @return The transformed pixel coordinates.
     */
    public static PixelCoordinate tileToPixelCoordinate(TileCoordinate tile) {
        return tileToPixelCoordinate(tile.x(), tile.y());
    }

    /**
     * Transforms a tile coordinate pair into the corresponding pixel coordinates (top-left corner).
     *
     * @param x The tile x coordinate.
     * @param y The tile y coordinate.
     * @return The transformed pixel coordinates.
     */
    public static PixelCoordinate tileToPixelCoordinate(int x, int y) {
        int pixelX = x * TILE_SIZE;
        int pixelY = y * TILE_SIZE;
        return new PixelCoordinate(pixelX, pixelY);
    }

    public BufferedImage sprite;
    public boolean collision = false;

    public Tile(BufferedImage sprite) {
        this(sprite, false);
    }

    public Tile(BufferedImage sprite, boolean collision) {
        this.sprite = sprite;
        this.collision = collision;
    }
}
