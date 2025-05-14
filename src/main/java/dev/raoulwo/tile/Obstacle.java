package dev.raoulwo.tile;

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

    public Obstacle(Tile tile, TileCoordinate coordinate) {
        this(tile, coordinate, 1, 1);
    }
}
