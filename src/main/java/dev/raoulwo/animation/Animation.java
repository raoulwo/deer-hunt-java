package dev.raoulwo.animation;

import java.util.List;

import dev.raoulwo.entity.Entity;
import dev.raoulwo.graphics.Graphics;

public class Animation {
    private final List<AnimationSprite> sprites;
    private int currentFrame = 0;
    private int currentSprite = 0;

    public Animation(List<AnimationSprite> sprites) {
        this.sprites = sprites;
    }

    public void play(Entity entity, Graphics g) {
        AnimationSprite animationSprite = sprites.get(currentSprite);
        g.drawSprite(animationSprite.sprite(), entity.x, entity.y);

        currentFrame++;
        if (currentFrame >= animationSprite.duration()) {
            currentFrame = 0;
            currentSprite++;
        }

        if (currentSprite >= sprites.size()) {
            currentSprite = 0;
        }
    }

    public void reset() {
        currentFrame = 0;
        currentSprite = 0;
    }
}

