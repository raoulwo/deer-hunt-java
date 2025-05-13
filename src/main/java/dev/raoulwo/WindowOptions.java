package dev.raoulwo;

import dev.raoulwo.graphics.Graphics;

public record WindowOptions(String title, int width, int height) {
    public static final String DEFAULT_WINDOW_TITLE = "Deer Hunt";

    // The actual screen dimensions calculated by scaled tile size and number of tiles.
    public static final int DEFAULT_WIDTH = Graphics.TARGET_RESOLUTION_WIDTH * Graphics.SCALE;
    public static final int DEFAULT_HEIGHT = Graphics.TARGET_RESOLUTION_HEIGHT * Graphics.SCALE;

    public WindowOptions() {
        this(DEFAULT_WINDOW_TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
