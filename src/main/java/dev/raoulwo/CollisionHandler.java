package dev.raoulwo;

import dev.raoulwo.entity.Entity;
import dev.raoulwo.tile.Tile;

import java.awt.*;

/**
 * Class responsible for performing collision checks. Can be replaced with more sophisticated system later on.
 */
public class CollisionHandler {
    GamePanel gamePanel;

    public CollisionHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTileCollision(Entity entity) {
        // Calculate the world coordinates of the collision box bounds.
        Rectangle coll = entity.collisionBox;
        int worldLeftBound = entity.worldX + coll.x;
        int worldRightBound = entity.worldX + coll.x + coll.width;
        int worldTopBound = entity.worldY + coll.y;
        int worldBottomBound = entity.worldY + coll.y + coll.height;

        // Calculate the indexes of surrounding player tiles.
        int leftCol = worldLeftBound / gamePanel.scaledTileSize;
        int rightCol = worldRightBound / gamePanel.scaledTileSize;
        int topRow = worldTopBound / gamePanel.scaledTileSize;
        int bottomRow = worldBottomBound / gamePanel.scaledTileSize;

        // Perform the collision check. The collision can only happen between two tiles, which tiles depends on the
        // direction of the moving entity.
        String mapTileCode1, mapTileCode2;

        // TODO: Messy collision detection code, can clean that up later if time.
        // If we instead have tile-based movement, we don't need this collision detection logic anyways.

        switch (entity.direction) {
            case UP: {
                // Check if the entity has collided with the top-left or top-right tiles.
                topRow = (worldTopBound - entity.speed) / gamePanel.scaledTileSize;
                mapTileCode1 = gamePanel.tileManager.map[topRow][leftCol];
                mapTileCode2 = gamePanel.tileManager.map[topRow][rightCol];
                Tile tile1 = gamePanel.tileManager.getTileByMapCode(mapTileCode1);
                Tile tile2 = gamePanel.tileManager.getTileByMapCode(mapTileCode2);
                if (tile1.collision || tile2.collision) {
                    entity.hasCollided = true;
                }
                break;
            }
            case DOWN: {
                // Check if the entity has collided with the bottom-left or bottom-right tiles.
                bottomRow = (worldBottomBound + entity.speed) / gamePanel.scaledTileSize;
                mapTileCode1 = gamePanel.tileManager.map[bottomRow][leftCol];
                mapTileCode2 = gamePanel.tileManager.map[bottomRow][rightCol];
                Tile tile1 = gamePanel.tileManager.getTileByMapCode(mapTileCode1);
                Tile tile2 = gamePanel.tileManager.getTileByMapCode(mapTileCode2);
                if (tile1.collision || tile2.collision) {
                    entity.hasCollided = true;
                }
                break;
            }
            case LEFT: {
                // Check if the entity has collided with the top-left or bottom-left tiles.
                leftCol = (worldLeftBound - entity.speed) / gamePanel.scaledTileSize;
                mapTileCode1 = gamePanel.tileManager.map[topRow][leftCol];
                mapTileCode2 = gamePanel.tileManager.map[bottomRow][leftCol];
                Tile tile1 = gamePanel.tileManager.getTileByMapCode(mapTileCode1);
                Tile tile2 = gamePanel.tileManager.getTileByMapCode(mapTileCode2);
                if (tile1.collision || tile2.collision) {
                    entity.hasCollided = true;
                }
                break;
            }
            case RIGHT: {
                // Check if the entity has collided with the top-right or bottom-right tiles.
                rightCol = (worldRightBound + entity.speed) / gamePanel.scaledTileSize;
                mapTileCode1 = gamePanel.tileManager.map[topRow][rightCol];
                mapTileCode2 = gamePanel.tileManager.map[bottomRow][rightCol];
                Tile tile1 = gamePanel.tileManager.getTileByMapCode(mapTileCode1);
                Tile tile2 = gamePanel.tileManager.getTileByMapCode(mapTileCode2);
                if (tile1.collision || tile2.collision) {
                    entity.hasCollided = true;
                }
                break;
            }
        }

    }

    // TODO: For now, checks whether the player or another entity has collided with an item.
    // Returns a boolean since we only have a hardcoded item.
    public boolean checkItemCollision(Entity entity, boolean player) {
        if (gamePanel.item == null) {
            return false;
        }
        if (gamePanel.item.pickedUp) {
            return false;
        }

        boolean collided = false;

        // Update the entities collision box.
        entity.collisionBox.x += entity.worldX;
        entity.collisionBox.y += entity.worldY;

        // Update the items collision box.
        gamePanel.item.collisionBox.x += gamePanel.item.worldX;
        gamePanel.item.collisionBox.y += gamePanel.item.worldY;

        switch (entity.direction) {
            case UP: {
                entity.collisionBox.y -= entity.speed;
                if (entity.collisionBox.intersects(gamePanel.item.collisionBox)) {
                    System.out.println("Collision with item: UP");
                    // TODO: For now, only players can collide with items.
                    collided = player;
                }
                break;
            }
            case DOWN: {
                entity.collisionBox.y += entity.speed;
                if (entity.collisionBox.intersects(gamePanel.item.collisionBox)) {
                    System.out.println("Collision with item: DOWN");
                    collided = player;
                }
                break;
            }
            case LEFT: {
                entity.collisionBox.x -= entity.speed;
                if (entity.collisionBox.intersects(gamePanel.item.collisionBox)) {
                    System.out.println("Collision with item: LEFT");
                    collided = player;
                }
                break;
            }
            case RIGHT: {
                entity.collisionBox.x += entity.speed;
                if (entity.collisionBox.intersects(gamePanel.item.collisionBox)) {
                    System.out.println("Collision with item: RIGHT");
                    collided = player;
                }
                break;
            }
        }

        // Reset the updated collision boxes.
        entity.collisionBox.x = entity.collisionBoxDefaultX;
        entity.collisionBox.y = entity.collisionBoxDefaultY;
        gamePanel.item.collisionBox.x = gamePanel.item.collisionBoxDefaultX;
        gamePanel.item.collisionBox.y = gamePanel.item.collisionBoxDefaultY;

        return collided;
    }

}
