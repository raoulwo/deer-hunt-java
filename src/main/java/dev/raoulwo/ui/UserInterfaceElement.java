package dev.raoulwo.ui;

import java.awt.image.BufferedImage;

public class UserInterfaceElement {
    public BufferedImage image;

    public int x, y;
    public int width, height;
    public int scale;

    public UserInterfaceElement(BufferedImage image, int x, int y, int width, int height, int scale) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
        this.scale = scale;
    }
}
