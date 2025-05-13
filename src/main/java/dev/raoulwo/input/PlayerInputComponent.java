package dev.raoulwo.input;

import dev.raoulwo.entity.Direction;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.State;

public class PlayerInputComponent implements InputComponent {
    private InputManager input = InputManager.instance();

    // Minimum number of frames a movement key needs to be held before you can move.
    private final static int MIN_FRAMES_BEFORE_MOVE = 3;

    private int moveCounter = 0;

    @Override
    public void update(Entity entity) {
        if (entity.moving) return;

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
