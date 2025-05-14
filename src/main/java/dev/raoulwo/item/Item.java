//package dev.raoulwo.item;
//
//import dev.raoulwo.Panel;
//import dev.raoulwo.entity.OldPlayer;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//
//public class Item {
//    public BufferedImage sprite;
//    public String name;
//    public int worldX, worldY;
//    public Rectangle collisionBox;
//    public int collisionBoxDefaultX = 0;
//    public int collisionBoxDefaultY = 0;
//    public boolean pickedUp = false;
//
//    private Panel gamePanel;
//
//    public Item(Panel gamePanel) {
//        this.gamePanel = gamePanel;
//        collisionBox = new Rectangle(0, 0, gamePanel.scaledTileSize, gamePanel.scaledTileSize);
//    }
//
//    public void draw(Graphics2D g2d) {
//        OldPlayer player = gamePanel.player;
//        int screenX = worldX - player.worldX + player.screenX;
//        int screenY = worldY - player.worldY + player.screenY;
//
//        // Check if tiles are within screen boundaries before rendering them.
//        if (worldX > player.worldX - player.screenX - gamePanel.scaledTileSize &&
//                worldX < player.worldX + player.screenX + gamePanel.scaledTileSize &&
//                worldY > player.worldY - player.screenY - gamePanel.scaledTileSize &&
//                worldY < player.worldY + player.screenY + gamePanel.scaledTileSize) {
//            g2d.drawImage(sprite, screenX, screenY, gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);
//        }
//    }
//}
