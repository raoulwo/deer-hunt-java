package dev.raoulwo.graphics;

import dev.raoulwo.entity.Entity;

/**
 * The graphics component of a game entity.
 */
public interface GraphicsComponent {
    /**
     * Draws the game entity, should be called from the game render method.
     * @param entity The entity to be rendered.
     * @param g The {@link Graphics} object for rendering graphics.
     */
    void draw(Entity entity, Graphics g);
}

