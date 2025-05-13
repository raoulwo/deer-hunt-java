package dev.raoulwo.item;

import dev.raoulwo.Panel;
import dev.raoulwo.resource.Resource;

public class Heart extends Item {
    public Heart(Panel gamePanel) {
        super(gamePanel);

        try {
            sprite = Resource.loadSprite("/sprites/items/", "heart.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
