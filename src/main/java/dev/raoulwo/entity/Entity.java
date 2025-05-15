package dev.raoulwo.entity;

import dev.raoulwo.World;
import dev.raoulwo.graphics.GraphicsComponent;
import dev.raoulwo.graphics.PlayerGraphicsComponent;
import dev.raoulwo.input.InputComponent;
import dev.raoulwo.input.NonPlayerInputComponent;
import dev.raoulwo.input.PlayerInputComponent;
import dev.raoulwo.physics.PhysicsComponent;
import dev.raoulwo.physics.PlayerPhysicsComponent;

public class Entity {
    // The name of the entity, must be unique.
    public String name;
    // The world pixel coordinates of the entity.
    public int x, y;

    public InputComponent input;
    public PhysicsComponent physics;
    public GraphicsComponent graphics;

    public Direction direction = Direction.DOWN;
    public State state = State.IDLE;
    public boolean moving = false;

    public static Entity createPlayer(String name) {
        return new Entity(name,
                new PlayerInputComponent(),
                new PlayerPhysicsComponent(),
                new PlayerGraphicsComponent(name));
    }

    public static Entity createNonPlayer(String name) {
        return new Entity(name,
                new NonPlayerInputComponent(),
                new PlayerPhysicsComponent(),
                new PlayerGraphicsComponent(name));
    }

    public Entity(String name, InputComponent input, PhysicsComponent physics, GraphicsComponent graphics) {
        this.name = name;
        this.input = input;
        this.physics = physics;
        this.graphics = graphics;
    }

    public void update(World world) {
        input.update(this);
        physics.update(world, this);
    }
}

