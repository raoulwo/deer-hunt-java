package dev.raoulwo;

import dev.raoulwo.graphics.Graphics;

/**
 * The window options that can be configured when creating the {@link Window} class.
 *
 * @param title The window title.
 * @param width The window width.
 * @param height The window height.
 */
public record WindowOptions(String title, int width, int height) {

    /**
     * The default window title.
     */
    public static final String DEFAULT_WINDOW_TITLE = "Deer Hunt";

    /**
     * The default window width.
     */
    public static final int DEFAULT_WIDTH = Graphics.TARGET_RESOLUTION_WIDTH * Graphics.SCALE;

    /**
     * The default window height.
     */
    public static final int DEFAULT_HEIGHT = Graphics.TARGET_RESOLUTION_HEIGHT * Graphics.SCALE;

    public WindowOptions() {
        this(DEFAULT_WINDOW_TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
