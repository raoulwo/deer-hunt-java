package dev.raoulwo.physics;

import dev.raoulwo.World;
import dev.raoulwo.entity.Entity;

public interface PhysicsComponent {
    void update(World world, Entity entity);

    /**
     *
     * @param world The game world instance.
     * @param entity The entity that is hit by the other entity.
     * @param other The entity that is hitting the entity.
     */
    void onHit(World world, Entity entity, Entity other);
}

