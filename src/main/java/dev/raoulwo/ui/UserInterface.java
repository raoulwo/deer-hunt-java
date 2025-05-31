package dev.raoulwo.ui;

import dev.raoulwo.Game;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.resource.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {

    private static UserInterface INSTANCE;

    private int timeElapsedSeconds;

    private BufferedImage portraitBorder;
    private BufferedImage portraitBlue;
    private BufferedImage portraitGreen;
    private BufferedImage portraitRed;
    private BufferedImage portraitYellow;

    public MenuElement menu;
    public PlayerOverlayElement greenOverlay;
    public PlayerOverlayElement redOverlay;
    public PlayerOverlayElement blueOverlay;
    public PlayerOverlayElement yellowOverlay;

    public static UserInterface instance() {
        if (INSTANCE == null) {
            INSTANCE = new UserInterface();
        }
        return INSTANCE;
    }

    private UserInterface() {
        try {
            portraitBorder = Resource.loadSprite("/ui/", "portrait_border.png");
            portraitBlue = Resource.loadSprite("/ui/", "portrait_blue.png");
            portraitGreen = Resource.loadSprite("/ui/", "portrait_green.png");
            portraitRed = Resource.loadSprite("/ui/", "portrait_red.png");
            portraitYellow = Resource.loadSprite("/ui/", "portrait_yellow.png");

            menu = new MenuElement(160 * 3, 64 * 3, 160, 144, 3);

            ButtonElement startGameButton = new ButtonElement(
                    160 * 3 + 48 * 3,
                    64 * 3 + 64 * 3,
                    64,
                    16,
                    3,
                    "START GAME");
            menu.buttons.add(startGameButton);

            ButtonElement quitGameButton = new ButtonElement(
                    160 * 3 + 48 * 3,
                    64 * 3 + 64 * 3 + 16 * 3 + 16,
                    64,
                    16,
                    3,
                    "QUIT GAME");
            menu.buttons.add(quitGameButton);

            menu.setActive(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Game game) {
        menu.update(game);
        timeElapsedSeconds = game.timeElapsedSeconds;
    }

    public void draw(Graphics g) {
        if (menu.isActive()) {
            menu.draw(g);
        } else {
            greenOverlay.draw(g);
            redOverlay.draw(g);
            blueOverlay.draw(g);
            yellowOverlay.draw(g);
            g.drawText(String.valueOf(Game.GAME_TIME - timeElapsedSeconds), 680, 80, 64.0f, Color.WHITE);
        }
    }

    public void createPlayerOverlay(Entity player) {
        if (player.name.equals("green")) {
            greenOverlay = new PlayerOverlayElement(32, 32, 48, 48, 2, player, portraitGreen, portraitBorder);
            greenOverlay.portraitX = 32 + 10;
            greenOverlay.portraitY = 32 + 10;
            greenOverlay.portraitWidth = 38;
            greenOverlay.portraitHeight = 38;
            greenOverlay.scoreX = 175;
            greenOverlay.scoreY = 100;
        } else if (player.name.equals("red")) {
            redOverlay = new PlayerOverlayElement(Graphics.TOTAL_WIDTH - 32 - 96, 32, 48, 48, 2, player, portraitRed, portraitBorder);
            redOverlay.portraitX = Graphics.TOTAL_WIDTH - 32 - 96 + 10;
            redOverlay.portraitY = 32 + 10;
            redOverlay.portraitWidth = 38;
            redOverlay.portraitHeight = 38;
            redOverlay.scoreX = 1225;
            redOverlay.scoreY = 100;
        } else if (player.name.equals("yellow")) {
            yellowOverlay = new PlayerOverlayElement(Graphics.TOTAL_WIDTH - 32 - 96, Graphics.TOTAL_HEIGHT - 32 - 96, 48, 48, 2, player, portraitYellow, portraitBorder);
            yellowOverlay.portraitX = Graphics.TOTAL_WIDTH - 32 - 96 + 10;
            yellowOverlay.portraitY = Graphics.TOTAL_HEIGHT - 32 + 10 - 96;
            yellowOverlay.portraitWidth = 38;
            yellowOverlay.portraitHeight = 38;
            yellowOverlay.scoreX = 175;
            yellowOverlay.scoreY = 750;
        } else if (player.name.equals("blue")) {
            blueOverlay = new PlayerOverlayElement(32, Graphics.TOTAL_HEIGHT - 32 - 96, 48, 48, 2, player, portraitBlue, portraitBorder);
            blueOverlay.portraitX = 32 + 10;
            blueOverlay.portraitY = Graphics.TOTAL_HEIGHT - 32 + 10 - 96;
            blueOverlay.portraitWidth = 38;
            blueOverlay.portraitHeight = 38;
            blueOverlay.scoreX = 1225;
            blueOverlay.scoreY = 750;
        }
    }
}
