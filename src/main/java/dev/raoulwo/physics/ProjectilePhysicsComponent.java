package dev.raoulwo.physics;

import dev.raoulwo.World;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;

public class ProjectilePhysicsComponent implements PhysicsComponent {

    private int speed = 8;

    @Override
    public void update(World world, Entity entity) {
        TileCoordinate tileCoordinate = Tile.pixelToTileCoordinate(entity.x, entity.y);
        Obstacle obstacle = switch (entity.direction) {
            case UP -> world.obstacles[tileCoordinate.x()][Math.max(tileCoordinate.y() - 1, 0)];
            case DOWN -> world.obstacles[tileCoordinate.x()][Math.min(tileCoordinate.y() + 1, World.MAX_LEVEL_ROWS - 1)];
            case LEFT -> world.obstacles[Math.max(tileCoordinate.x() - 1, 0)][tileCoordinate.y()];
            case RIGHT -> world.obstacles[Math.min(tileCoordinate.x() + 1, World.MAX_LEVEL_COLUMNS - 1)][tileCoordinate.y()];
        };
        Entity otherEntity = switch (entity.direction) {
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
                if (otherEntity != null && entity.y < coordinate.y()) {
                    entity.y = coordinate.y();
                    onEntityHit(world, entity, otherEntity);
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
                if (otherEntity != null && entity.y > coordinate.y()) {
                    entity.y = coordinate.y();
                    onEntityHit(world, entity, otherEntity);
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
                if (otherEntity != null && entity.x < coordinate.x()) {
                    entity.x = coordinate.x();
                    onEntityHit(world, entity, otherEntity);
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
                if (otherEntity != null && entity.x > coordinate.x()) {
                    entity.x = coordinate.x();
                    onEntityHit(world, entity, otherEntity);
                }
            }
        }
    }

    private void onBoundHit(World world, Entity entity) {
        world.removeEntity(entity);
    }

    private void onObstacleHit(World world, Entity entity, Obstacle obstacle) {
        world.removeEntity(entity);
    }

    private void onEntityHit(World world, Entity entity, Entity other) {
        world.removeEntity(entity);
        // TODO: Trigger a hit on the other entity.
    }
}
