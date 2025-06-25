package dev.raoulwo.tile;

/**
 * Represents an obstacle in the game world.
 */
public class Obstacle {
    public Tile tile;

    public TileCoordinate coordinate;
    public int width, height; // Number of tiles.

    public Obstacle(Tile tile, TileCoordinate coordinate, int width, int height) {
        this.tile = tile;
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;
    }
}
