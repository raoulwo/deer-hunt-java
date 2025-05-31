package dev.raoulwo.graphics;

import dev.raoulwo.resource.Resource;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.ui.UserInterfaceElement;
import dev.raoulwo.util.PixelCoordinate;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Graphics {
    public static final int TARGET_RESOLUTION_WIDTH = 480;
    public static final int TARGET_RESOLUTION_HEIGHT = 270;

    // Factor by which we'll scale the tiles.
    public static final int SCALE = 3;

    // Original tile size in pixels.
    public static final int ORIGINAL_TILE_SIZE = 16;
    // Scaled tile size in pixels calculated by original tile size and scale factor.
    public static final int SCALED_TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    public static final int TOTAL_WIDTH = TARGET_RESOLUTION_WIDTH * SCALE;
    public static final int TOTAL_HEIGHT = TARGET_RESOLUTION_HEIGHT * SCALE;

    private final Graphics2D g;
    private final Camera camera = Camera.instance();
    private Font font;

    public Graphics(final Graphics2D g) {
        this.g = g;


        try {
            font = Resource.loadFont("/ui/", "font.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawTile(BufferedImage image, int x, int y) {
        PixelCoordinate pixel = Tile.tileToPixelCoordinate(x, y);
        int screenX = pixel.x() - camera.x + Camera.SCREEN_X;
        int screenY = pixel.y() - camera.y + Camera.SCREEN_Y;
        if (camera.isVisible(pixel.x(), pixel.y())) {
            g.drawImage(image, screenX, screenY, Graphics.SCALED_TILE_SIZE, Graphics.SCALED_TILE_SIZE, null);
        }
    }

    public void drawSprite(BufferedImage image, int x, int y) {
        int screenX = x - camera.x + Camera.SCREEN_X;
        int screenY = y - camera.y + Camera.SCREEN_Y;
        g.drawImage(image, screenX, screenY, Graphics.SCALED_TILE_SIZE, Graphics.SCALED_TILE_SIZE, null);
    }

    public void drawImage(BufferedImage image, int x, int y, int width, int height) {
        g.drawImage(image, x, y, width, height, null);
    }

    public void drawObstacle(Obstacle obstacle, int x, int y) {
        if (x == obstacle.coordinate.x() && y == obstacle.coordinate.y()) {
            PixelCoordinate pixel = Tile.tileToPixelCoordinate(x, y);
            int screenX = pixel.x() - camera.x + Camera.SCREEN_X;
            int screenY = pixel.y() - camera.y + Camera.SCREEN_Y;
            g.drawImage(obstacle.tile.sprite, screenX, screenY, obstacle.width * SCALED_TILE_SIZE, obstacle.height * SCALED_TILE_SIZE, null);
        }
    }

    public void drawRect(Color color, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRect( x, y, width, height);
    }

    public void drawUserInterfaceElement(UserInterfaceElement element) {
        g.drawImage(element.getImage(),
                element.getX(),
                element.getY(),
                element.getWidth() * element.getScale(),
                element.getHeight() * element.getScale(),
                null);
    }

    public void drawText(String text, int x, int y, float fontSize, Color color) {
        g.setFont(font.deriveFont(fontSize));
        g.setColor(color);
        g.drawString(text, x, y);
    }
}

