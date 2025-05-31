package dev.raoulwo.ui;

import java.awt.image.BufferedImage;

public abstract class BaseUserInterfaceElement implements UserInterfaceElement {
    protected int x, y;
    protected int width, height;
    protected int scale;

    protected boolean active = false;

    protected BufferedImage image;

    public BaseUserInterfaceElement(int x, int y, int width, int height, int scale) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getScale() {
        return scale;
    }
}
