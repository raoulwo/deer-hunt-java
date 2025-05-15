package dev.raoulwo.physics;

import dev.raoulwo.World;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.State;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;

public class PlayerPhysicsComponent implements PhysicsComponent {

    // NOTE: The tile size should be evenly divisible by the player speed, else the player coordinates
    // get messed up and the bound checks don't work!
    private int speed = 4;
    private int pixelsMoved = 0;

    @Override
    public void update(World world, Entity entity) {
        switch (entity.state) {
            case IDLE -> idle(world,entity);
            case WALK -> walk(world, entity);
            case ATTACK -> attack(world, entity);
        }
    }

    @Override
    public void onHit(World world, Entity entity, Entity other) {
        entity.state = State.ATTACKED;
    }

    private void walk(World world, Entity entity) {
        if (!entity.moving) {
            return;
        }

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
                    entity.y = 0;
                    break;
                }
                entity.y -= speed;
                var coordinate = Tile.tileToPixelCoordinate(0, tileCoordinate.y());
                if (obstacle != null && entity.y < coordinate.y()) {
                    entity.y = coordinate.y();
                }
                if (otherEntity != null && entity.y < coordinate.y()) {
                    entity.y = coordinate.y();
                }
            }
            case DOWN -> {
                int bound = (World.MAX_LEVEL_ROWS - 1) * Graphics.SCALED_TILE_SIZE;
                if (entity.y >= bound) {
                    entity.y = bound;
                    break;
                }
                entity.y += speed;
                var coordinate = Tile.tileToPixelCoordinate(0, tileCoordinate.y());
                if (obstacle != null && entity.y > coordinate.y()) {
                    entity.y = coordinate.y();
                }
                if (otherEntity != null && entity.y > coordinate.y()) {
                    entity.y = coordinate.y();
                }
            }
            case LEFT -> {
                if (entity.x <= 0) {
                    entity.x = 0;
                    break;
                }
                entity.x -= speed;
                var coordinate = Tile.tileToPixelCoordinate(tileCoordinate.x(), 0);
                if (obstacle != null && entity.x < coordinate.x()) {
                    entity.x = coordinate.x();
                }
                if (otherEntity != null && entity.x < coordinate.x()) {
                    entity.x = coordinate.x();
                }
            }
            case RIGHT -> {
                int bound = (World.MAX_LEVEL_COLUMNS - 1) * Graphics.SCALED_TILE_SIZE;
                if (entity.x >= bound) {
                    entity.x = bound;
                    break;
                }
                entity.x += speed;
                var coordinate = Tile.tileToPixelCoordinate(tileCoordinate.x(), 0);
                if (obstacle != null && entity.x > coordinate.x()) {
                    entity.x = coordinate.x();
                }
                if (otherEntity != null && entity.x > coordinate.x()) {
                    entity.x = coordinate.x();
                }
            }
        }

        pixelsMoved += speed;
        if (pixelsMoved >= Graphics.SCALED_TILE_SIZE) {
            entity.moving = false;
            pixelsMoved = 0;
        }
    }

    private void idle(World world, Entity entity) {
    }

    private void attack(World world, Entity entity) {
        if (entity.attacking) {
            return;
        }

        TileCoordinate playerPosition = Tile.pixelToTileCoordinate(entity.x, entity.y);
        TileCoordinate projectilePosition = switch (entity.direction) {
            case UP -> new TileCoordinate(playerPosition.x(), playerPosition.y() - 1);
            case DOWN -> new TileCoordinate(playerPosition.x(), playerPosition.y() + 1);
            case LEFT -> new TileCoordinate(playerPosition.x() - 1, playerPosition.y());
            case RIGHT -> new TileCoordinate(playerPosition.x() + 1, playerPosition.y());
        };

        if (projectilePosition.y() < 0 || projectilePosition.y() >= World.MAX_LEVEL_ROWS) {
            return;
        }

        if (projectilePosition.x() < 0 || projectilePosition.x() >= World.MAX_LEVEL_COLUMNS) {
            return;
        }

        Entity projectile = Entity.createProjectile("projectile_" + System.currentTimeMillis(), projectilePosition, entity.direction);
        world.entities.put(projectile.name, projectile);
    }
}

