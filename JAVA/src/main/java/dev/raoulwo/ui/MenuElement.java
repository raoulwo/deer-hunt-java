package dev.raoulwo.ui;

import dev.raoulwo.Game;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.resource.Resource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A menu UI element.
 */
public class MenuElement extends BaseUserInterfaceElement implements UserInterfaceElement {
    public String title = "DEER HUNT";

    public List<ButtonElement> buttons = new ArrayList<>();

    public MenuElement(int x, int y, int width, int height, int scale) {
        super(x, y, width, height, scale);
        try {
            image = Resource.loadSprite("/ui/", "menu.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Game game) {
        if (!active) return;

        for (ButtonElement button : buttons) {
            button.update(game);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!active) return;

        g.drawUserInterfaceElement(this);
        for (ButtonElement button : buttons) {
            button.draw(g);
        }
        g.drawText(title, x + width / 2, y + height / 2 + 24, 48, Color.WHITE);
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
        for (ButtonElement button : buttons) {
            button.setActive(active);
        }
    }
}

