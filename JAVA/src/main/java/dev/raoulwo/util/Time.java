package dev.raoulwo.util;

/**
 * Represents the elapsed time between frames used for time sensitive operations.
 */
public class Time {
    public static final int TARGET_FRAMES_PER_SECOND = 60;
    public static final double TARGET_SECONDS_PER_FRAME =  1.0 / TARGET_FRAMES_PER_SECOND;
    public static final double TARGET_MILLISECONDS_PER_FRAME =  1_000 * TARGET_SECONDS_PER_FRAME;
    public static final double TARGET_NANOSECONDS_PER_FRAME =  1_000_000_000 * TARGET_SECONDS_PER_FRAME;

    // The elapsed time between frames in milliseconds.
    private static double deltaTime;

    // Prevent instantiation.
    private Time() {}

    /**
     * Returns the elapsed time between the last two frames in seconds.
     *
     * @return The elapsed time between the last two frames in seconds.
     */
    public static double deltaTime() {
        return deltaTime / 1_000;
    }

    /**
     * Sets the elapsed time between the last two frames in milliseconds. This
     * method should only be called from within the game loop.
     *
     * @param elapsed The elapsed time between the last two frames in milliseconds.
     */
    public static void updateDeltaTime(double elapsed) {
        deltaTime = elapsed;
    }
}

