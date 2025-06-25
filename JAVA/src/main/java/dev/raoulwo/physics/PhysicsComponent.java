package dev.raoulwo.physics;

import dev.raoulwo.World;
import dev.raoulwo.entity.Entity;

/**
 * The physics component of a game entity.
 */
public interface PhysicsComponent {
    /**
     * Handles physics updates of an entity. Includes things like collision detection.
     * @param world The game world the entity resides in.
     * @param entity The entity of which the physics are updated.
     */
    void update(World world, Entity entity);

    /**
     * Method that is called once a collision between two entities occurs.
     * @param world The game world instance.
     * @param entity The entity that is hit by the other entity.
     * @param other The entity that is hitting the entity.
     */
    void onHit(World world, Entity entity, Entity other);
}

