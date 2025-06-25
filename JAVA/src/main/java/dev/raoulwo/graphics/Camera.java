package dev.raoulwo.graphics;

/**
 * Singleton class that represents the game camera centered on the player.
 */
public class Camera {
    // Camera world position in pixels.
    public int x, y;
    // Screen dimensions
    public int width, height;

    public static int SCREEN_X = Graphics.TARGET_RESOLUTION_WIDTH * Graphics.SCALE / 2 - Graphics.SCALED_TILE_SIZE / 2;
    public static int SCREEN_Y = Graphics.TARGET_RESOLUTION_HEIGHT * Graphics.SCALE / 2 - Graphics.SCALED_TILE_SIZE / 2;

    private static Camera INSTANCE;

    public static Camera instance() {
        if (INSTANCE == null) {
            INSTANCE = new Camera(Graphics.TARGET_RESOLUTION_WIDTH * Graphics.SCALE, Graphics.TARGET_RESOLUTION_HEIGHT);
        }
        return INSTANCE;
    }

    private Camera(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Centers the camera on given pixel coordinates.
     * @param x The x pixel coordinate.
     * @param y The y pixel coordinate.
     */
    public void centerOn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if given world coordinates are in camera frame.
     * @param worldX The x world coordinate.
     * @param worldY The y world coordinate.
     * @return True if the given world coordinates are within camera frame.
     */
    public boolean isVisible(int worldX, int worldY) {
        int scaledTileSize = Graphics.SCALED_TILE_SIZE;

        return worldX > x - SCREEN_X - scaledTileSize &&
                worldX < x + SCREEN_X + scaledTileSize &&
                worldY > y - SCREEN_Y - scaledTileSize &&
                worldY < y + SCREEN_Y + scaledTileSize;
    }
}
