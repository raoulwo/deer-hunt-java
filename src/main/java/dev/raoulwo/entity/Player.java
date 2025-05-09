package dev.raoulwo.entity;

import dev.raoulwo.GamePanel;
import dev.raoulwo.KeyHandler;
import dev.raoulwo.resource.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Player extends Entity {
    public enum PlayerColor {
        BLUE, GREEN, RED, YELLOW
    }

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    HashMap<Direction, BufferedImage> idle = new HashMap<>();
    HashMap<Direction, List<BufferedImage>> walking = new HashMap<>();

    int walkingAnimIndex = 0; // Tracks the current walking animation sprite index.
    int walkingAnimCounter = 0; // Tracks the number of elapsed frames used to choose the right animation sprite.
    int walkingAnimInterval = 8; // Number of frames for a single walking animation sprite.

    PlayerColor color = PlayerColor.GREEN;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - gamePanel.scaledTileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.scaledTileSize / 2;
        worldX = gamePanel.scaledTileSize * 12;
        worldY = gamePanel.scaledTileSize * 10;
        speed = 8;

        collisionBox = new Rectangle();
        collisionBox.x = 8;
        collisionBox.y = 16;
        collisionBox.width = 32;
        collisionBox.height = 32;

        try {
            loadPlayerSprites(color);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Move this logic into its own class for loading assets.
    public void loadPlayerSprites(PlayerColor color) throws IOException {
        String idlePath = "/sprites/players/ninja_" + color.name().toLowerCase() + "/idle/";
        idle.put(Direction.DOWN, Resource.loadSprite(idlePath, "idle_south.png"));
        idle.put(Direction.UP, Resource.loadSprite(idlePath, "idle_north.png"));
        idle.put(Direction.LEFT, Resource.loadSprite(idlePath, "idle_west.png"));
        idle.put(Direction.RIGHT, Resource.loadSprite(idlePath, "idle_east.png"));

        String walkingPath = "/sprites/players/ninja_" + color.name().toLowerCase() + "/walk/";
        walking.put(Direction.DOWN, Resource.loadSprites(walkingPath,
                "walk_south_01.png",
                "walk_south_02.png",
                "walk_south_03.png",
                "walk_south_04.png"
        ));
        walking.put(Direction.UP, Resource.loadSprites(walkingPath,
                "walk_north_01.png",
                "walk_north_02.png",
                "walk_north_03.png",
                "walk_north_04.png"
        ));
        walking.put(Direction.LEFT, Resource.loadSprites(walkingPath,
                "walk_west_01.png",
                "walk_west_02.png",
                "walk_west_03.png",
                "walk_west_04.png"
        ));
        walking.put(Direction.RIGHT, Resource.loadSprites(walkingPath,
                "walk_east_01.png",
                "walk_east_02.png",
                "walk_east_03.png",
                "walk_east_04.png"
        ));
    }

    public void update() {
        if (!isWalking()) {
            return;
        }

        // Update player direction.
        if (keyHandler.upPressed) {
            direction = Direction.UP;
        } else if (keyHandler.downPressed) {
            direction = Direction.DOWN;
        } else if (keyHandler.leftPressed) {
            direction = Direction.LEFT;
        } else if (keyHandler.rightPressed) {
            direction = Direction.RIGHT;
        }

        // Check for collisions with terrain.
        hasCollided = false;
        gamePanel.collisionHandler.checkTileCollision(this);

        // Update the player position if no collision.
        if (!hasCollided) {
            switch (direction) {
                case UP:
                    worldY -= speed;
                    break;
                case DOWN:
                    worldY += speed;
                    break;
                case LEFT:
                    worldX -= speed;
                    break;
                case RIGHT:
                    worldX += speed;
                    break;
            }
        }

        // TODO: Clean up the animation logic, probably needs to be done when we have a lot more possible player states.
        walkingAnimCounter++;
        if (walkingAnimCounter >= walkingAnimInterval) {
            final int WALKING_ANIM_SPRITE_COUNT = 4;
            walkingAnimIndex = (walkingAnimIndex + 1) % WALKING_ANIM_SPRITE_COUNT;
            walkingAnimCounter = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        // Render the sprite depending on player state: Currently we only differentiate between walking and idle.
        BufferedImage sprite = null;
        if (isWalking()) {
            sprite = walking.get(direction).get(walkingAnimIndex);
        } else {
            sprite = idle.get(direction);
        }

        g2d.drawImage(sprite, screenX, screenY, gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);
    }

    public boolean isWalking() {
        return keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
    }
}
