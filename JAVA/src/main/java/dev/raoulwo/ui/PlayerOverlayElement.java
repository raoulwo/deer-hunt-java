package dev.raoulwo.ui;

import dev.raoulwo.Game;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.graphics.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A player overlay UI element.
 */
public class PlayerOverlayElement extends BaseUserInterfaceElement implements UserInterfaceElement {

    private Entity player;

    private BufferedImage portrait;
    private BufferedImage portraitBorder;

    public int portraitX, portraitY, scoreX, scoreY;
    public int portraitWidth, portraitHeight;

    public enum Position {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    public PlayerOverlayElement(int x, int y, int width, int height, int scale, Entity player, BufferedImage portrait,
                                BufferedImage portraitBorder) {
        super(x, y, width, height, scale);

        this.player = player;
        this.portrait = portrait;
        this.portraitBorder = portraitBorder;
    }

    @Override
    public void update(Game game) {
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(portraitBorder, x, y, width * scale, height * scale);
        g.drawImage(portrait, portraitX, portraitY, portraitWidth * scale, portraitHeight * scale);
        g.drawText(player.getScore() + "", scoreX, scoreY, 64.0f, Color.WHITE);
    }
}
