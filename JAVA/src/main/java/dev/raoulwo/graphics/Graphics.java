package dev.raoulwo.graphics;

import dev.raoulwo.resource.Resource;
import dev.raoulwo.tile.Obstacle;
import dev.raoulwo.tile.Tile;
import dev.raoulwo.ui.UserInterfaceElement;
import dev.raoulwo.util.PixelCoordinate;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * An adapter for the underlying Java graphics API.
 */
public class Graphics {
    /**
     * The target resolution width in pixels.
     */
    public static final int TARGET_RESOLUTION_WIDTH = 480;
    /**
     * The target resolution height in pixels.
     */
    public static final int TARGET_RESOLUTION_HEIGHT = 270;

    /**
     * Factor by which the pixel tiles should be scaled.
     */
    public static final int SCALE = 3;

    /**
     * The original tile size in pixels.
     */
    public static final int ORIGINAL_TILE_SIZE = 16;
    /**
     * The scaled tile size in pixels.
     */
    public static final int SCALED_TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    /**
     * The total width.
     */
    public static final int TOTAL_WIDTH = TARGET_RESOLUTION_WIDTH * SCALE;
    /**
     * The total height.
     */
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

    /**
     * Draws a sprite at given pixel coordinates.
     * @param image The sprite to be rendered.
     * @param x The x pixel coordinate.
     * @param y The y pixel coordinate.
     */
    public void drawSprite(BufferedImage image, int x, int y) {
        int screenX = x - camera.x + Camera.SCREEN_X;
        int screenY = y - camera.y + Camera.SCREEN_Y;
        g.drawImage(image, screenX, screenY, Graphics.SCALED_TILE_SIZE, Graphics.SCALED_TILE_SIZE, null);
    }

    /**
     * Draws a sprite with given width and height at given pixel coordinates.
     * @param image The sprite to be rendered.
     * @param x The x pixel coordinate.
     * @param y The y pixel coordinate.
     * @param width The width of the sprite.
     * @param height The height of the sprite.
     */
    public void drawImage(BufferedImage image, int x, int y, int width, int height) {
        g.drawImage(image, x, y, width, height, null);
    }

    /**
     * Draws a sprite with given width and height at given pixel coordinates relative to the game camera.
     * @param image The sprite to be rendered.
     * @param x The x pixel coordinate.
     * @param y The y pixel coordinate.
     * @param width The width of the sprite.
     * @param height The height of the sprite.
     */
    public void drawImageRelativeToCamera(BufferedImage image, int x, int y, int width, int height) {
        int screenX = x - camera.x + Camera.SCREEN_X;
        int screenY = y - camera.y + Camera.SCREEN_Y;
        g.drawImage(image, screenX, screenY, width, height, null);
    }

    /**
     * Draws an obstacle at given tile coordinates.
     * @param obstacle The obstacle to be rendered.
     * @param x The x tile coordinate.
     * @param y The y tile coordinate.
     */
    public void drawObstacle(Obstacle obstacle, int x, int y) {
        if (x == obstacle.coordinate.x() && y == obstacle.coordinate.y()) {
            PixelCoordinate pixel = Tile.tileToPixelCoordinate(x, y);
            int screenX = pixel.x() - camera.x + Camera.SCREEN_X;
            int screenY = pixel.y() - camera.y + Camera.SCREEN_Y;
            g.drawImage(obstacle.tile.sprite, screenX, screenY, obstacle.width * SCALED_TILE_SIZE, obstacle.height * SCALED_TILE_SIZE, null);
        }
    }

    /**
     * Draws given UI element.
     * @param element The UI element to be rendered.
     */
    public void drawUserInterfaceElement(UserInterfaceElement element) {
        g.drawImage(element.getImage(),
                element.getX(),
                element.getY(),
                element.getWidth() * element.getScale(),
                element.getHeight() * element.getScale(),
                null);
    }

    /**
     * Draws given text.
     * @param text The text to be rendered.
     * @param x The x pixel coordinate.
     * @param y The y pixel coordinate.
     * @param fontSize The font size of the text.
     * @param color The color of the text.
     */
    public void drawText(String text, int x, int y, float fontSize, Color color) {
        g.setFont(font.deriveFont(fontSize));
        g.setColor(color);
        g.drawString(text, x, y);
    }
}

