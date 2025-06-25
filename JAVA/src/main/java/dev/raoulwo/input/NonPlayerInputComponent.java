package dev.raoulwo.input;

import dev.raoulwo.World;
import dev.raoulwo.entity.Direction;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.State;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.tile.TileCoordinate;

import java.util.List;

/**
 * The input component of a non player entity.
 */
public class NonPlayerInputComponent implements InputComponent {

    private final static int ATTACKED_STATE_DURATION = 30;
    // Invulnerability period after getting hit by a projectile.
    private final static int ATTACKED_STATE_COOLDOWN = 30;

    private final static int ATTACK_STATE_DURATION = 30;
    private final static int ATTACK_STATE_COOLDOWN = 30;

    private int attackedDurationCounter = 0;
    private int attackedCooldownCounter = ATTACKED_STATE_COOLDOWN;
    private int attackDurationCounter = 0;
    private int attackCooldownCounter = ATTACK_STATE_COOLDOWN;

    private int moveCounter = 0;

    private String monkeyTarget = null;

    @Override
    public void update(World world, Entity entity) {
        if (entity.state == State.ATTACKED) {
            attackedDurationCounter++;
            if (attackedDurationCounter >= ATTACKED_STATE_DURATION) {
                entity.state = State.IDLE;
                attackedDurationCounter = 0;
            } else {
                return;
            }
        }

        if (entity.moving) return;

        if (entity.state == State.ATTACK) {
            entity.attacking = true;
            attackDurationCounter++;
            if (attackDurationCounter >= ATTACK_STATE_DURATION) {
                entity.state = State.IDLE;
                entity.attacking = false;
                attackDurationCounter = 0;
            } {
                return;
            }
        }

        attackCooldownCounter++;

        if (monkeyTarget == null || world.entities.get(monkeyTarget) == null) {
            monkeyTarget = getNewMonkeyTarget(world);
        }

        Entity monkey = world.entities.get(monkeyTarget);
        if (monkey == null) {
            return;
        }

        if (isMonkeyAbove(world, entity, monkey)) {
            attackOrMove(entity, Direction.UP);
        } else if (isMonkeyBelow(world, entity, monkey)) {
            attackOrMove(entity, Direction.DOWN);
        } else if (isMonkeyLeft(world, entity, monkey)) {
            attackOrMove(entity, Direction.LEFT);
        } else if (isMonkeyRight(world, entity, monkey)) {
            attackOrMove(entity, Direction.RIGHT);
        } else {
            TileCoordinate entityTile = Tile.pixelToTileCoordinate(entity.x, entity.y);
            TileCoordinate monkeyTile = Tile.pixelToTileCoordinate(monkey.x, monkey.y);

            boolean isMonkeyToTheLeft = monkeyTile.x() < entityTile.x();
            boolean isMonkeyAbove = monkeyTile.y() < entityTile.y();

            int zeroOrOne = (int)(Math.random() * 2);
            if (zeroOrOne == 0) {
                if (isMonkeyToTheLeft) {
                    moveToDirection(entity, Direction.LEFT);
                } else {
                    moveToDirection(entity, Direction.RIGHT);
                }
            } else {
                if (isMonkeyAbove) {
                    moveToDirection(entity, Direction.UP);
                } else {
                    moveToDirection(entity, Direction.DOWN);
                }
            }
        }
    }

    private boolean isMonkeyAbove(World world, Entity entity, Entity monkey) {
        TileCoordinate entityTile = Tile.pixelToTileCoordinate(entity.x, entity.y);
        TileCoordinate monkeyTile = Tile.pixelToTileCoordinate(monkey.x, monkey.y);
        return entityTile.x() == monkeyTile.x() && entityTile.y() > monkeyTile.y();
    }

    private boolean isMonkeyBelow(World world, Entity entity, Entity monkey) {
        TileCoordinate entityTile = Tile.pixelToTileCoordinate(entity.x, entity.y);
        TileCoordinate monkeyTile = Tile.pixelToTileCoordinate(monkey.x, monkey.y);
        return entityTile.x() == monkeyTile.x() && entityTile.y() < monkeyTile.y();
    }

    private boolean isMonkeyLeft(World world, Entity entity, Entity monkey) {
        TileCoordinate entityTile = Tile.pixelToTileCoordinate(entity.x, entity.y);
        TileCoordinate monkeyTile = Tile.pixelToTileCoordinate(monkey.x, monkey.y);
        return entityTile.x() > monkeyTile.x() && entityTile.y() == monkeyTile.y();
    }

    private boolean isMonkeyRight(World world, Entity entity, Entity monkey) {
        TileCoordinate entityTile = Tile.pixelToTileCoordinate(entity.x, entity.y);
        TileCoordinate monkeyTile = Tile.pixelToTileCoordinate(monkey.x, monkey.y);
        return entityTile.x() < monkeyTile.x() && entityTile.y() == monkeyTile.y();
    }

    private String getNewMonkeyTarget(World world) {
        List<Entity> monkeys = world.entities
                .values()
                .stream()
                .filter(entity -> entity.name.contains("monkey"))
                .toList();

        int randomIndex = (int) (Math.random() * monkeys.size());
        return monkeys.get(randomIndex).name;
    }

    private void attackOrMove(Entity entity, Direction direction) {
        if (entity.direction == direction) {
            attack(entity);
        } else {
            moveToDirection(entity, direction);
        }
    }

    private void moveToDirection(Entity entity, Direction direction) {
        entity.direction = direction;
        entity.state = State.WALK;
        entity.moving = true;
    }

    private void attack(Entity entity) {
        if (attackCooldownCounter >= ATTACK_STATE_COOLDOWN) {
            entity.state = State.ATTACK;
            attackCooldownCounter = 0;
        }
    }
}

