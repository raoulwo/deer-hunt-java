package dev.raoulwo.animation;

import java.awt.image.BufferedImage;

/**
 * Represents a sprite that's part of an animation.
 *
 * @param sprite The sprite to be shown during the animation.
 * @param duration The duration for how many frames the sprite should be shown.
 */
public record AnimationSprite(BufferedImage sprite, int duration) {}
