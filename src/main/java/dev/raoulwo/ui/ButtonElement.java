package dev.raoulwo.ui;

import dev.raoulwo.Game;
import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.input.InputManager;
import dev.raoulwo.resource.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ButtonElement extends BaseUserInterfaceElement implements UserInterfaceElement {

    public enum ButtonState {
        NORMAL, HOVER, PRESSED;
    }

    private ButtonState state = ButtonState.NORMAL;

    private BufferedImage hoverImage;
    private BufferedImage pressedImage;

    private String text;

    private InputManager input = InputManager.instance();

    public ButtonElement(int x, int y, int width, int height, int scale, String text) {
        super(x, y, width, height, scale);

        this.text = text;

        try {
            image = Resource.loadSprite("/ui/", "button.png");
            hoverImage = Resource.loadSprite("/ui/", "button_hover.png");
            pressedImage = Resource.loadSprite("/ui/", "button_pressed.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Game game) {
        if (!active) return;

        int mouseX = input.mouseX;
        int mouseY = input.mouseY;

        if (mouseX >= x && mouseX <= x + width * 3 && mouseY >= y && mouseY <= y + height * 3) {
            if (input.mousePressed) {
                state = ButtonState.PRESSED;
                onClick(game);
            } else {
                state = ButtonState.HOVER;
            }
        } else {
            state = ButtonState.NORMAL;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!active) return;

        g.drawUserInterfaceElement(this);
        g.drawText(text, x + 8, y + 32, 24.0f, Color.WHITE);
    }

    public void onClick(Game game) {
        if (!active) return;

        if (text.equals("QUIT GAME")) {
            System.exit(0);
        }

        if (text.equals("START GAME")) {
            UserInterface.instance().menu.setActive(false);
            game.startGame();
        }
    }

    @Override
    public BufferedImage getImage() {
        return switch (state) {
            case NORMAL -> image;
            case HOVER -> hoverImage;
            case PRESSED -> pressedImage;
        };
    }
}


