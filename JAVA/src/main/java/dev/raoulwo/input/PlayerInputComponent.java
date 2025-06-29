package dev.raoulwo.input;

import dev.raoulwo.World;
import dev.raoulwo.entity.Direction;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.State;

/**
 * The input component of a player entity.
 */
public class PlayerInputComponent implements InputComponent {
    private InputManager input = InputManager.instance();

    // Minimum number of frames a movement key needs to be held before you can move.
    private final static int MIN_FRAMES_BEFORE_MOVE = 3;

    private final static int ATTACK_STATE_DURATION = 30;
    private final static int ATTACK_STATE_COOLDOWN = 30;

    private final static int ATTACKED_STATE_DURATION = 30;
    // Invulnerability period after getting hit by a projectile.
    private final static int ATTACKED_STATE_COOLDOWN = 30;

    private int moveCounter = 0;
    private int attackDurationCounter = 0;
    private int attackCooldownCounter = ATTACK_STATE_COOLDOWN;
    private int attackedDurationCounter = 0;
    private int attackedCooldownCounter = ATTACKED_STATE_COOLDOWN;

    @Override
    public void update(World world, Entity entity) {
        if (entity.state == State.ATTACKED) {
            attackedDurationCounter++;
            if (attackedDurationCounter >= ATTACKED_STATE_DURATION) {
                entity.state = State.IDLE;
                entity.moving = false;
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

        if (input.spacePressed) {
            if (attackCooldownCounter >= ATTACK_STATE_COOLDOWN) {
                entity.state = State.ATTACK;
                attackCooldownCounter = 0;
                return;
            }
        }
        if (!input.moveKeyPressed()) {
            entity.state = State.IDLE;
            return;
        }

        // Have to press a movement key for a couple of frames before you can move.
        moveCounter++;
        if (moveCounter >= MIN_FRAMES_BEFORE_MOVE) {
            entity.state = State.WALK;
            entity.moving = true;
            moveCounter = 0;
        }

        if (input.upPressed) {
            entity.direction = Direction.UP;
        } else if (input.downPressed) {
            entity.direction = Direction.DOWN;
        } else if (input.leftPressed) {
            entity.direction = Direction.LEFT;
        } else if (input.rightPressed) {
            entity.direction = Direction.RIGHT;
        }
    }
}

