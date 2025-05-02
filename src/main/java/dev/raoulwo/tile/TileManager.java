package dev.raoulwo.tile;

import dev.raoulwo.GamePanel;
import dev.raoulwo.entity.Player;
import dev.raoulwo.resource.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class TileManager {
    GamePanel gamePanel;

    // Contains the plaintext encoded tile information for the map of a level. We could later on have different maps
    // if we wanted to.
    String[][] map;

    // Maps the tile codes used for serializing map layouts to the corresponding tile names.
    HashMap<String, String> tileCodeToTileName = new HashMap<>();

    // Maps the names of all tiles to their corresponding tile objects.
    HashMap<String, Tile> tiles = new HashMap<>();

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tileCodeToTileName.put("0", "floor_01");
        tileCodeToTileName.put("1", "floor_02");
        tileCodeToTileName.put("2", "floor_03");
        tileCodeToTileName.put("3", "floor_04");
        tileCodeToTileName.put("4", "floor_05");
        tileCodeToTileName.put("5", "floor_06");
        tileCodeToTileName.put("6", "floor_07");
        tileCodeToTileName.put("7", "floor_08");
        tileCodeToTileName.put("8", "floor_09");
        tileCodeToTileName.put("9", "floor_10");
        tileCodeToTileName.put("a", "floor_11");
        tileCodeToTileName.put("b", "floor_12");
        tileCodeToTileName.put("c", "floor_13");
        tileCodeToTileName.put("d", "floor_14");
        tileCodeToTileName.put("e", "floor_15");
        tileCodeToTileName.put("f", "floor_16");
        tileCodeToTileName.put("g", "floor_17");
        tileCodeToTileName.put("h", "floor_18");
        tileCodeToTileName.put("i", "floor_19");
        tileCodeToTileName.put("j", "floor_20");
        tileCodeToTileName.put("k", "floor_21");
        tileCodeToTileName.put("l", "floor_22");
        tileCodeToTileName.put("m", "floor_23");
        tileCodeToTileName.put("n", "floor_24");
        tileCodeToTileName.put("o", "floor_25");
        tileCodeToTileName.put("p", "floor_26");
        tileCodeToTileName.put("q", "floor_27");
        tileCodeToTileName.put("r", "floor_28");
        tileCodeToTileName.put("s", "floor_29");
        tileCodeToTileName.put("t", "floor_30");
        tileCodeToTileName.put("u", "floor_31");
        tileCodeToTileName.put("v", "floor_32");
        tileCodeToTileName.put("w", "floor_33");
        tileCodeToTileName.put("x", "floor_34");
        tileCodeToTileName.put("y", "floor_35");
        tileCodeToTileName.put("z", "floor_36");
        tileCodeToTileName.put("A", "floor_37");
        tileCodeToTileName.put("B", "floor_38");
        tileCodeToTileName.put("C", "floor_39");
        tileCodeToTileName.put("D", "floor_40");
        tileCodeToTileName.put("E", "floor_41");
        tileCodeToTileName.put("F", "floor_42");
        tileCodeToTileName.put("G", "floor_43");
        tileCodeToTileName.put("H", "floor_44");
        tileCodeToTileName.put("I", "floor_45");
        tileCodeToTileName.put("J", "floor_46");
        tileCodeToTileName.put("K", "floor_47");
        tileCodeToTileName.put("L", "floor_48");
        tileCodeToTileName.put("M", "floor_49");
        tileCodeToTileName.put("N", "floor_50");
        tileCodeToTileName.put("O", "floor_51");
        tileCodeToTileName.put("P", "floor_52");
        tileCodeToTileName.put("Q", "floor_53");
        tileCodeToTileName.put("R", "floor_54");

        try {
            loadTiles();
            map = Resource.loadMap("/maps/", "map_01.txt");
        } catch (IOException e) {
            System.out.println("Error loading tiles");
            e.printStackTrace();
        }
    }

    void loadTiles() throws IOException {
        String pathPrefix = "/sprites/tiles/floor/";

        for (int i = 1; i <= 54; i++) {
            String file = String.format("floor_%02d", i);
            BufferedImage sprite = Resource.loadSprite(pathPrefix, file + ".png");

            tiles.put(file, new Tile(sprite));
        }
    }

    public void draw(Graphics2D g2d) {
        for (int row = 0; row < gamePanel.maxWorldRows; row++) {
            for (int col = 0; col < gamePanel.maxWorldColumns; col++) {
                int worldX = col * gamePanel.scaledTileSize;
                int worldY = row * gamePanel.scaledTileSize;

                Player player = gamePanel.player;
                int screenX = worldX - player.worldX + player.screenX;
                int screenY = worldY - player.worldY + player.screenY;

                String code = map[row][col];
                String key = tileCodeToTileName.get(code);

                // Check if tiles are within screen boundaries before rendering them.
                if (worldX > player.worldX - player.screenX - gamePanel.scaledTileSize &&
                    worldX < player.worldX + player.screenX + gamePanel.scaledTileSize &&
                    worldY > player.worldY - player.screenY - gamePanel.scaledTileSize &&
                    worldY < player.worldY + player.screenY + gamePanel.scaledTileSize) {
                    g2d.drawImage(tiles.get(key).sprite, screenX, screenY, gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);
                }
            }
        }
    }
}
