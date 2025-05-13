package dev.raoulwo.item;

import dev.raoulwo.GamePanel;
import dev.raoulwo.entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item {
    public BufferedImage sprite;
    public String name;
    public int worldX, worldY;

    private GamePanel gamePanel;

    public Item(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2d) {
        Player player = gamePanel.player;
        int screenX = worldX - player.worldX + player.screenX;
        int screenY = worldY - player.worldY + player.screenY;

        // Check if tiles are within screen boundaries before rendering them.
        if (worldX > player.worldX - player.screenX - gamePanel.scaledTileSize &&
                worldX < player.worldX + player.screenX + gamePanel.scaledTileSize &&
                worldY > player.worldY - player.screenY - gamePanel.scaledTileSize &&
                worldY < player.worldY + player.screenY + gamePanel.scaledTileSize) {
            g2d.drawImage(sprite, screenX, screenY, gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);
        }
    }
}
