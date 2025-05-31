package dev.raoulwo.ui;

import dev.raoulwo.Game;
import dev.raoulwo.graphics.Graphics;

import java.awt.image.BufferedImage;

public interface UserInterfaceElement {
    void update(Game game);
    void draw(Graphics g);
    boolean isActive();
    void setActive(boolean active);

    BufferedImage getImage();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    int getScale();
}

