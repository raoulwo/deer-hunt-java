package dev.raoulwo.entity;

import dev.raoulwo.animation.Animation;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.graphics.GraphicsComponent;
import dev.raoulwo.resource.Resource;

import java.io.IOException;

public class MonkeyGraphicsComponent implements GraphicsComponent {
    private Animation dead;

    private static final int DEAD_ANIM_FRAME_DURATION = 1;

    public MonkeyGraphicsComponent() {
        try {
            initializeAnimations();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeAnimations() throws IOException {

        String deadPath = "/sprites/monkey/";
        this.dead = Resource.loadAnimation(
                DEAD_ANIM_FRAME_DURATION,
                deadPath,
                "dead.png");
    }

    @Override
    public void draw(Entity entity, Graphics g) {
        switch (entity.state) {
            case IDLE -> idle(entity, g);
            case WALK -> {
            }
            case ATTACK -> {
            }
            case ATTACKED -> {
            }
            case DEAD -> {
            }
        }
    }

    private void idle(Entity entity, Graphics g) {
        dead.play(entity, g);
    }
}
