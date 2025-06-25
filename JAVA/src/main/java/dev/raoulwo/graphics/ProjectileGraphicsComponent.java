package dev.raoulwo.graphics;

import dev.raoulwo.entity.Entity;
import dev.raoulwo.resource.Resource;

import java.awt.image.BufferedImage;

public class ProjectileGraphicsComponent implements GraphicsComponent {

    private BufferedImage sprite;

    private static final Camera camera = Camera.instance();

    public ProjectileGraphicsComponent() {
        try {
            sprite = Resource.loadSprite("/sprites/projectiles/", "shuriken.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Entity entity, Graphics g) {
        entity.screenX = entity.x - camera.x + Camera.SCREEN_X;
        entity.screenY = entity.y - camera.y + Camera.SCREEN_Y;
        g.drawSprite(sprite, entity.x, entity.y);
    }
}
