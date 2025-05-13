package dev.raoulwo.physics;

import dev.raoulwo.World;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;

public class PlayerPhysicsComponent implements PhysicsComponent {

    private int speed = 3;
    private int pixelsMoved = 0;

    @Override
    public void update(World world, Entity entity) {
        switch (entity.state) {
            case IDLE -> idle(world,entity);
            case WALK -> walk(world, entity);
        }
    }

    private void walk(World world, Entity entity) {
        if (!entity.moving) {
            return;
        }

        TileCoordinate tile = Tile.pixelToTileCoordinate(entity.x, entity.y);
        Tile obstacle = switch (entity.direction) {
            case UP -> world.obstacles[tile.x()][tile.y() - 1];
            case DOWN -> world.obstacles[tile.x()][tile.y() + 1];
            case LEFT -> world.obstacles[tile.x() - 1][tile.y()];
            case RIGHT -> world.obstacles[tile.x() + 1][tile.y()];
        };

        switch (entity.direction) {
            case UP -> {
                var coordinate = Tile.tileToPixelCoordinate(0, tile.y());
                entity.y -= speed;
                if (obstacle != null && entity.y < coordinate.y()) {
                    entity.y = coordinate.y();
                }
            }
            case DOWN -> {
                var coordinate = Tile.tileToPixelCoordinate(0, tile.y());
                entity.y += speed;
                if (obstacle != null && entity.y > coordinate.y()) {
                    entity.y = coordinate.y();
                }
            }
            case LEFT -> {
                var coordinate = Tile.tileToPixelCoordinate(tile.x(), 0);
                entity.x -= speed;
                if (obstacle != null && entity.x < coordinate.x()) {
                    entity.x = coordinate.x();
                }
            }
            case RIGHT -> {
                var coordinate = Tile.tileToPixelCoordinate(tile.x(), 0);
                entity.x += speed;
                if (obstacle != null && entity.x > coordinate.x()) {
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
}
