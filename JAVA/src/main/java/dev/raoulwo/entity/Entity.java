package dev.raoulwo.entity;

import dev.raoulwo.World;
import dev.raoulwo.graphics.*;
import dev.raoulwo.input.*;
import dev.raoulwo.physics.MonkeyPhysicsComponent;
import dev.raoulwo.physics.PhysicsComponent;
import dev.raoulwo.physics.PlayerPhysicsComponent;
import dev.raoulwo.physics.ProjectilePhysicsComponent;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;
import dev.raoulwo.util.PixelCoordinate;

/**
 * Represents the game entities, acting as a container for various components.
 */
public class Entity {
    // The name of the entity, it must be unique.
    public String name;
    // The world pixel coordinates of the entity.
    public int x, y;

    public int screenX = Graphics.TARGET_RESOLUTION_WIDTH * Graphics.SCALE / 2 - Graphics.SCALED_TILE_SIZE / 2;
    public int screenY = Graphics.TARGET_RESOLUTION_HEIGHT * Graphics.SCALE / 2 - Graphics.SCALED_TILE_SIZE / 2;

    public InputComponent input;
    public PhysicsComponent physics;
    public GraphicsComponent graphics;

    public Direction direction = Direction.DOWN;
    public State state = State.IDLE;
    public boolean moving = false;
    public boolean attacking = false;

    private int score = 0;

    public Entity(String name, InputComponent input, PhysicsComponent physics, GraphicsComponent graphics) {
        this.name = name;
        this.input = input;
        this.physics = physics;
        this.graphics = graphics;
    }

    /**
     * Factory method to create a player entity.
     *
     * @param name Name of the player entity, must be unique.
     * @param centerCamera Center camera on entity if true.
     * @return The newly created player entity.
     */
    public static Entity createPlayer(String name, boolean centerCamera) {
        return new Entity(name,
                new PlayerInputComponent(),
                new PlayerPhysicsComponent(),
                new PlayerGraphicsComponent(name, centerCamera));
    }

    /**
     * Factory method to create a non player character.
     *
     * @param name Name of the non player character, must be unique.
     * @return The newly created non player character.
     */
    public static Entity createNonPlayer(String name) {
        return new Entity(name,
                new NonPlayerInputComponent(),
                new PlayerPhysicsComponent(),
                new PlayerGraphicsComponent(name, false));
    }

    /**
     * Factory method to create a monkey entity.
     *
     * @param name Name of the monkey, must be unique.
     * @return The newly created monkey entity.
     */
    public static Entity createMonkey(String name) {
        return new Entity(name,
                new MonkeyInputComponent(),
                new MonkeyPhysicsComponent(),
                new MonkeyGraphicsComponent()
                );
    }

    /**
     * Factory method to create a projectile entity.
     *
     * @param name Name of the projectile entity, must be unique.
     * @param position Tile coordinate the projectile should be spawned at.
     * @param direction Direction the projectile should move in.
     * @return The newly created projectile entity.
     */
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

    /**
     * Updates the entity, called from within the game loop.
     *
     * @param world The world the entity resides in.
     */
    public void update(World world) {
        input.update(world, this);
        physics.update(world, this);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void reduceScore(int score) {
        this.score -= score;
        if (this.score < 0) {
            this.score = 0;
        }
    }
}

