package dev.raoulwo.entity;

public class Entity {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public int worldX, worldY;
    public int speed;
    public Direction direction = Direction.DOWN;
}
