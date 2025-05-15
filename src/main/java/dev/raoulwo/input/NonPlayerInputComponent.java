package dev.raoulwo.input;

import dev.raoulwo.entity.Entity;
import dev.raoulwo.entity.State;

public class NonPlayerInputComponent implements InputComponent {

    private final static int ATTACKED_STATE_DURATION = 30;
    // Invulnerability period after getting hit by a projectile.
    private final static int ATTACKED_STATE_COOLDOWN = 30;

    private int attackedDurationCounter = 0;
    private int attackedCooldownCounter = ATTACKED_STATE_COOLDOWN;

    @Override
    public void update(Entity entity) {
        if (entity.state == State.ATTACKED) {
            attackedDurationCounter++;
            if (attackedDurationCounter >= ATTACKED_STATE_DURATION) {
                entity.state = State.IDLE;
                attackedDurationCounter = 0;
            } else {
                return;
            }
        }

    }
}

