package dev.raoulwo.input;

import dev.raoulwo.World;
import dev.raoulwo.entity.Direction;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.State;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;

/**
 * The input component of a monkey entity..
 */
public class MonkeyInputComponent implements InputComponent {
    private final static int ATTACKED_STATE_DURATION = 30;
    private int attackedDurationCounter = 0;

    @Override
    public void update(World world, Entity entity) {
        if (entity.state == State.ATTACKED) {
            attackedDurationCounter++;
            if (attackedDurationCounter >= ATTACKED_STATE_DURATION) {
                world.entities.remove(entity.name);
                world.spawnMonkey();
            } else {
                return;
            }
        }

        if (entity.moving) {
            return;
        }

        if (isPlayerAbove(world, entity)) {
            entity.direction = Direction.DOWN;
            entity.state = State.WALK;
            entity.moving = true;
        } else if (isPlayerBelow(world, entity)) {
            entity.direction = Direction.UP;
            entity.state = State.WALK;
            entity.moving = true;
        } else if (isPlayerLeft(world, entity)) {
            entity.direction = Direction.RIGHT;
            entity.state = State.WALK;
            entity.moving = true;
        } else if (isPlayerRight(world, entity)) {
            entity.direction = Direction.LEFT;
            entity.state = State.WALK;
            entity.moving = true;
        } else {
            entity.state = State.IDLE;
        }
    }

    private boolean isPlayerAbove(World world, Entity entity) {
        TileCoordinate tile = Tile.pixelToTileCoordinate(entity.x, entity.y);

        for (int y = tile.y(); y >= 0; y--) {
            Entity other = world.getEntity(tile.x(), y);
            if (other == null) {
                continue;
            }
            if (other.name.equals("red") || other.name.equals("blue")
            || other.name.equals("green") || other.name.equals("yellow")) {
                return true;
            }
        }

        return false;
    }

    private boolean isPlayerBelow(World world, Entity entity) {
        TileCoordinate tile = Tile.pixelToTileCoordinate(entity.x, entity.y);

        for (int y = tile.y(); y < World.MAX_LEVEL_ROWS; y++) {
            Entity other = world.getEntity(tile.x(), y);
            if (other == null) {
                continue;
            }
            if (other.name.equals("red") || other.name.equals("blue")
                    || other.name.equals("green") || other.name.equals("yellow")) {
                return true;
            }
        }

        return false;
    }

    private boolean isPlayerLeft(World world, Entity entity) {
        TileCoordinate tile = Tile.pixelToTileCoordinate(entity.x, entity.y);

        for (int x = tile.x(); x >= 0; x--) {
            Entity other = world.getEntity(x, tile.y());
            if (other == null) {
                continue;
            }
            if (other.name.equals("red") || other.name.equals("blue")
                    || other.name.equals("green") || other.name.equals("yellow")) {
                return true;
            }
        }

        return false;
    }

    private boolean isPlayerRight(World world, Entity entity) {
        TileCoordinate tile = Tile.pixelToTileCoordinate(entity.x, entity.y);

        for (int x = tile.x(); x < World.MAX_LEVEL_COLUMNS; x++) {
            Entity other = world.getEntity(x, tile.y());
            if (other == null) {
                continue;
            }
            if (other.name.equals("red") || other.name.equals("blue")
                    || other.name.equals("green") || other.name.equals("yellow")) {
                return true;
            }
        }

        return false;
    }
}
