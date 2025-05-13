package dev.raoulwo.physics;

import dev.raoulwo.World;
import dev.raoulwo.entity.Entity;

public interface PhysicsComponent {
    void update(World world, Entity entity);
}

