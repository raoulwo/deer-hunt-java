package dev.raoulwo.ui;

import dev.raoulwo.graphics.Graphics;
import dev.raoulwo.resource.Resource;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterface {
    public List<UserInterfaceElement> portraitBorders = new ArrayList<>();
    public Map<String, UserInterfaceElement> portraits = new HashMap<>();

    private static UserInterface INSTANCE;

    public static UserInterface instance() {
        if (INSTANCE == null) {
            INSTANCE = new UserInterface();
        }
        return INSTANCE;
    }

    private UserInterface() {
        try {
            BufferedImage portraitBorderImage = Resource.loadSprite("/ui/", "portrait_border.png");
            portraitBorders.add(new UserInterfaceElement(portraitBorderImage, 32, 32, 48, 48, 2));
            portraitBorders.add(new UserInterfaceElement(portraitBorderImage, 32, Graphics.TOTAL_HEIGHT - 32 - 96, 48, 48, 2));
            portraitBorders.add(new UserInterfaceElement(portraitBorderImage, Graphics.TOTAL_WIDTH - 32 - 96, 32, 48, 48, 2));
            portraitBorders.add(new UserInterfaceElement(portraitBorderImage, Graphics.TOTAL_WIDTH - 32 - 96, Graphics.TOTAL_HEIGHT - 32 - 96, 48, 48, 2));

            BufferedImage portraitBlue = Resource.loadSprite("/ui/", "portrait_blue.png");
            BufferedImage portraitGreen = Resource.loadSprite("/ui/", "portrait_green.png");
            BufferedImage portraitRed = Resource.loadSprite("/ui/", "portrait_red.png");
            BufferedImage portraitYellow = Resource.loadSprite("/ui/", "portrait_yellow.png");
            portraits.put("green", new UserInterfaceElement(portraitGreen, 32 + 10, 32 + 10, 38, 38, 2));
            portraits.put("blue", new UserInterfaceElement(portraitBlue, 32 + 10, Graphics.TOTAL_HEIGHT - 32 + 10 - 96, 38, 38, 2));
            portraits.put("red", new UserInterfaceElement(portraitRed, Graphics.TOTAL_WIDTH - 32 - 96 + 10, Graphics.TOTAL_HEIGHT - 32 + 10 - 96, 38, 38, 2));
            portraits.put("yellow", new UserInterfaceElement(portraitYellow, Graphics.TOTAL_WIDTH - 32 - 96 + 10, 32 + 10, 38, 38, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
