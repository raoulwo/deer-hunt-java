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
}
