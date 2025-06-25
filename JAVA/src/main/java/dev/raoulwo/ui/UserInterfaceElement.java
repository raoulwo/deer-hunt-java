package dev.raoulwo.ui;

import dev.raoulwo.Game;
import dev.raoulwo.graphics.Graphics;

import java.awt.image.BufferedImage;

/**
 * Represents a UI element.
 */
public interface UserInterfaceElement {
    /**
     * Updates the UI element, called from within the game loop.
     * @param game The game instance.
     */
    void update(Game game);

    /**
     * Renders the UI element, called from within the game's render method.
     * @param g The {@link Graphics} object used for rendering graphics.
     */
    void draw(Graphics g);

    /**
     * Indicates whether the UI element is active or not.
     * @return True if the element is active.
     */
    boolean isActive();

    /**
     * Sets the UI element to be active/inactive.
     * @param active Boolean to set the UI element to active/inactive.
     */
    void setActive(boolean active);

    /**
     * Getter method for the UI element's sprite.
     * @return The UI element's sprite.
     */
    BufferedImage getImage();

    /**
     * Getter method for the x pixel coordinate.
     * @return The x pixel coordinate.
     */
    int getX();

    /**
     * Getter method for the y pixel coordinate.
     * @return The y pixel coordinate.
     */
    int getY();

    /**
     * Getter method for the element's width.
     * @return The width of the element.
     */
    int getWidth();

    /**
     * Getter method for the element's height.
     * @return The height of the element.
     */
    int getHeight();

    /**
     * Returns the scale at which the element should be rendered at.
     * @return The scale of the UI element.
     */
    int getScale();
}

