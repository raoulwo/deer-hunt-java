package dev.raoulwo.graphics;

import dev.raoulwo.entity.Direction;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.resource.Resource;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ProjectileGraphicsComponent implements GraphicsComponent {

    private HashMap<Direction, BufferedImage> sprites = new HashMap<>();

    public ProjectileGraphicsComponent() {
        try {
            // TODO: Hardcoded some sprites for now.
            var south = Resource.loadSprite("/sprites/players/ninja_yellow/idle/", "idle_south.png");
            var north = Resource.loadSprite("/sprites/players/ninja_yellow/idle/", "idle_north.png");
            var west = Resource.loadSprite("/sprites/players/ninja_yellow/idle/", "idle_west.png");
            var east = Resource.loadSprite("/sprites/players/ninja_yellow/idle/", "idle_east.png");
            sprites.put(Direction.DOWN, south);
            sprites.put(Direction.UP, north);
            sprites.put(Direction.LEFT, west);
            sprites.put(Direction.RIGHT, east);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Entity entity, Graphics g) {
        g.drawSprite(sprites.get(entity.direction), entity.x, entity.y);
    }
}
