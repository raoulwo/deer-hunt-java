package dev.raoulwo.entity;

import java.awt.*;

public class OldEntity {
    public int worldX, worldY;
    public int collisionBoxDefaultX, collisionBoxDefaultY;
    public int speed;
    // public Direction direction = Direction.DOWN;

    public Rectangle collisionBox;
    // TODO: Maybe instead use something like an observable pattern for collision notifications?
    public boolean hasCollided = false;
}


