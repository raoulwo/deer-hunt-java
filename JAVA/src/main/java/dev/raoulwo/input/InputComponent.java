package dev.raoulwo.input;

import dev.raoulwo.World;
import dev.raoulwo.entity.Entity;

public interface InputComponent {
    void update(World world, Entity entity);
}


