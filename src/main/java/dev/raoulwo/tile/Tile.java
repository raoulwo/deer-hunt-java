package dev.raoulwo.tile;

import java.awt.image.BufferedImage;

// TODO: Maybe can turn this into a record?
public class Tile {
    public BufferedImage sprite;
    // NOTE: Can be later on replaced with bit masks for more granular collision detection.
    public boolean collision = false;

    public Tile(BufferedImage sprite) {
        this.sprite = sprite;
    }
}
