package dev.raoulwo.input;

import dev.raoulwo.World;
import dev.raoulwo.entity.Entity;

/**
 * The input component of a game entity.
 */
public interface InputComponent {
    /**
     * Updates the inputs for an entity, called from within the game loop.
     * @param world The game world the entity resides in.
     * @param entity The entity of which the input should be updated.
     */
    void update(World world, Entity entity);
}


