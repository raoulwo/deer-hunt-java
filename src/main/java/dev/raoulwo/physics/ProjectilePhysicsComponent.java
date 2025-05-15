package dev.raoulwo.physics;

import dev.raoulwo.World;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.State;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;

public class ProjectilePhysicsComponent implements PhysicsComponent {

    private int speed = 8;

    // The dead counter is used to make sure that the projectile is removed after a certain amount of frames
    // and not just immediately so that it is rendered.
    private static int DURATION_BEFORE_DEAD = 10;
    private int deadCounter = 0;

    @Override
    public void update(World world, Entity entity) {
        if (entity.state == State.DEAD) {
            deadCounter++;
            if (deadCounter >= DURATION_BEFORE_DEAD) {
                world.removeEntity(entity);
            }
            return;
        }

        TileCoordinate tileCoordinate = Tile.pixelToTileCoordinate(entity.x, entity.y);
        Obstacle obstacle = switch (entity.direction) {
            case UP -> world.obstacles[tileCoordinate.x()][Math.max(tileCoordinate.y() - 1, 0)];
            case DOWN -> world.obstacles[tileCoordinate.x()][Math.min(tileCoordinate.y() + 1, World.MAX_LEVEL_ROWS - 1)];
            case LEFT -> world.obstacles[Math.max(tileCoordinate.x() - 1, 0)][tileCoordinate.y()];
            case RIGHT -> world.obstacles[Math.min(tileCoordinate.x() + 1, World.MAX_LEVEL_COLUMNS - 1)][tileCoordinate.y()];
        };
        Entity entityInDirection = switch (entity.direction) {
            case UP -> world.getEntity(new TileCoordinate(tileCoordinate.x(), tileCoordinate.y() - 1)) ;
            case DOWN -> world.getEntity(new TileCoordinate(tileCoordinate.x(), tileCoordinate.y() + 1));
            case LEFT -> world.getEntity(new TileCoordinate(tileCoordinate.x() - 1, tileCoordinate.y()));
            case RIGHT -> world.getEntity(new TileCoordinate(tileCoordinate.x() + 1, tileCoordinate.y()));
        };

        switch (entity.direction) {
            case UP -> {
                if (entity.y <= 0) {
                    onBoundHit(world, entity);
                    entity.y = 0;
                    break;
                }
                entity.y -= speed;
                var coordinate = Tile.tileToPixelCoordinate(0, tileCoordinate.y());
                if (obstacle != null && entity.y < coordinate.y()) {
                    entity.y = coordinate.y();
                    onObstacleHit(world, entity, obstacle);
                }
                if (entityInDirection != null && entity.y < coordinate.y()) {
                    entity.y = coordinate.y();
                    onEntityHit(world, entity, entityInDirection);
                }
            }
            case DOWN -> {
                int bound = (World.MAX_LEVEL_ROWS - 1) * Graphics.SCALED_TILE_SIZE;
                if (entity.y >= bound) {
                    onBoundHit(world, entity);
                    entity.y = bound;
                    break;
                }
                entity.y += speed;
                var coordinate = Tile.tileToPixelCoordinate(0, tileCoordinate.y());
                if (obstacle != null && entity.y > coordinate.y()) {
                    entity.y = coordinate.y();
                    onObstacleHit(world, entity, obstacle);
                }
                if (entityInDirection != null && entity.y > coordinate.y()) {
                    entity.y = coordinate.y();
                    onEntityHit(world, entity, entityInDirection);
                }
            }
            case LEFT -> {
                if (entity.x <= 0) {
                    onBoundHit(world, entity);
                    entity.x = 0;
                    break;
                }
                entity.x -= speed;
                var coordinate = Tile.tileToPixelCoordinate(tileCoordinate.x(), 0);
                if (obstacle != null && entity.x < coordinate.x()) {
                    entity.x = coordinate.x();
                    onObstacleHit(world, entity, obstacle);
                }
                if (entityInDirection != null && entity.x < coordinate.x()) {
                    entity.x = coordinate.x();
                    onEntityHit(world, entity, entityInDirection);
                }
            }
            case RIGHT -> {
                int bound = (World.MAX_LEVEL_COLUMNS - 1) * Graphics.SCALED_TILE_SIZE;
                if (entity.x >= bound) {
                    onBoundHit(world, entity);
                    entity.x = bound;
                    break;
                }
                entity.x += speed;
                var coordinate = Tile.tileToPixelCoordinate(tileCoordinate.x(), 0);
                if (obstacle != null && entity.x > coordinate.x()) {
                    entity.x = coordinate.x();
                    onObstacleHit(world, entity, obstacle);
                }
                if (entityInDirection != null && entity.x > coordinate.x()) {
                    entity.x = coordinate.x();
                    onEntityHit(world, entity, entityInDirection);
                }
            }
        }
    }

    @Override
    public void onHit(World world, Entity entity, Entity other) {
        if (other.name.contains("projectile")) {
            world.removeEntity(entity);
        }
    }

    private void onBoundHit(World world, Entity entity) {
        entity.state = State.DEAD;
    }

    private void onObstacleHit(World world, Entity entity, Obstacle obstacle) {
        entity.state = State.DEAD;
    }

    private void onEntityHit(World world, Entity entity, Entity other) {
        other.physics.onHit(world, other, entity);
        entity.state = State.DEAD;
    }
}
