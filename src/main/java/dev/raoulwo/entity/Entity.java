package dev.raoulwo.entity;

import dev.raoulwo.World;
import dev.raoulwo.graphics.GraphicsComponent;
import dev.raoulwo.graphics.PlayerGraphicsComponent;
import dev.raoulwo.graphics.ProjectileGraphicsComponent;
import dev.raoulwo.input.InputComponent;
import dev.raoulwo.input.NonPlayerInputComponent;
import dev.raoulwo.input.PlayerInputComponent;
import dev.raoulwo.input.ProjectileInputComponent;
import dev.raoulwo.physics.PhysicsComponent;
import dev.raoulwo.physics.PlayerPhysicsComponent;
import dev.raoulwo.physics.ProjectilePhysicsComponent;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;
import dev.raoulwo.util.PixelCoordinate;

public class Entity {
    // The name of the entity, it must be unique.
    public String name;
    // The world pixel coordinates of the entity.
    public int x, y;

    public InputComponent input;
    public PhysicsComponent physics;
    public GraphicsComponent graphics;

    public Direction direction = Direction.DOWN;
    public State state = State.IDLE;
    public boolean moving = false;

    public Entity(String name, InputComponent input, PhysicsComponent physics, GraphicsComponent graphics) {
        this.name = name;
        this.input = input;
        this.physics = physics;
        this.graphics = graphics;
    }

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

    public static Entity createProjectile(String name, TileCoordinate position, Direction direction) {
        Entity projectile = new Entity(name,
                new ProjectileInputComponent(),
                new ProjectilePhysicsComponent(),
                new ProjectileGraphicsComponent());
        PixelCoordinate pixel = Tile.tileToPixelCoordinate(position);
        projectile.x = pixel.x();
        projectile.y = pixel.y();
        projectile.direction = direction;

        return projectile;
    }

    public void update(World world) {
        input.update(this);
        physics.update(world, this);
    }
}

