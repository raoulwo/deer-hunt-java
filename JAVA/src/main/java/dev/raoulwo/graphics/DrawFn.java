package dev.raoulwo.graphics;

/**
 * An interface for a draw callback to render the game's graphics.
 */
@FunctionalInterface
public interface DrawFn {
    void draw(Graphics g);
}
