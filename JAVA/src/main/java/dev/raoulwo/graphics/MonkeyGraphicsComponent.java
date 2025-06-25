package dev.raoulwo.graphics;

import dev.raoulwo.animation.Animation;
import dev.raoulwo.entity.Direction;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.resource.Resource;

import java.io.IOException;
import java.util.HashMap;

/**
 * Graphics component used to render monkey entities.
 */
public class MonkeyGraphicsComponent implements GraphicsComponent {

    private static final int IDLE_ANIM_FRAME_DURATION = 1;
    private static final int WALK_ANIM_FRAME_DURATION = 15;
    private static final int DEAD_ANIM_FRAME_DURATION = 1;

    private final HashMap<Direction, Animation> idleAnimations = new HashMap<>();
    private final HashMap<Direction, Animation> walkAnimations = new HashMap<>();
    private Animation deadAnimation;

    public MonkeyGraphicsComponent() {
        try {
            initializeAnimations();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeAnimations() throws IOException {
        String idlePath = "/sprites/monkey/idle/";
        Animation idleSouthAnimation = Resource.loadAnimation(
                IDLE_ANIM_FRAME_DURATION,
                idlePath,
                "idle_south.png");
        idleAnimations.put(Direction.DOWN, idleSouthAnimation);
        Animation idleNorthAnimation = Resource.loadAnimation(
                IDLE_ANIM_FRAME_DURATION,
                idlePath,
                "idle_north.png");
        idleAnimations.put(Direction.UP, idleNorthAnimation);
        Animation idleWestAnimation = Resource.loadAnimation(
                IDLE_ANIM_FRAME_DURATION,
                idlePath,
                "idle_west.png");
        idleAnimations.put(Direction.LEFT, idleWestAnimation);
        Animation idleEastAnimation = Resource.loadAnimation(
                IDLE_ANIM_FRAME_DURATION,
                idlePath,
                "idle_east.png");
        idleAnimations.put(Direction.RIGHT, idleEastAnimation);

        String walkPath = "/sprites/monkey/walk/";
        Animation walkSouthAnimation = Resource.loadAnimation(
                WALK_ANIM_FRAME_DURATION,
                walkPath,
                "walk_south_01.png",
                "walk_south_02.png",
                "walk_south_03.png",
                "walk_south_04.png");
        walkAnimations.put(Direction.DOWN, walkSouthAnimation);
        Animation walkNorthAnimation = Resource.loadAnimation(
                WALK_ANIM_FRAME_DURATION,
                walkPath,
                "walk_north_01.png",
                "walk_north_02.png",
                "walk_north_03.png",
                "walk_north_04.png");
        walkAnimations.put(Direction.UP, walkNorthAnimation);
        Animation walkWestAnimation = Resource.loadAnimation(
                WALK_ANIM_FRAME_DURATION,
                walkPath,
                "walk_west_01.png",
                "walk_west_02.png",
                "walk_west_03.png",
                "walk_west_04.png");
        walkAnimations.put(Direction.LEFT, walkWestAnimation);
        Animation walkEastAnimation = Resource.loadAnimation(
                WALK_ANIM_FRAME_DURATION,
                walkPath,
                "walk_east_01.png",
                "walk_east_02.png",
                "walk_east_03.png",
                "walk_east_04.png");
        walkAnimations.put(Direction.RIGHT, walkEastAnimation);

        String deadPath = "/sprites/monkey/dead/";
        this.deadAnimation = Resource.loadAnimation(
                DEAD_ANIM_FRAME_DURATION,
                deadPath,
                "dead.png");
    }

    @Override
    public void draw(Entity entity, Graphics g) {
        switch (entity.state) {
            case IDLE -> idle(entity, g);
            case WALK -> walk(entity, g);
            case ATTACK -> attack(entity, g);
            case ATTACKED -> attacked(entity, g);
            case DEAD -> dead(entity, g);
        }
    }

    private void idle(Entity entity, Graphics g) {
        Animation idleAnimation = idleAnimations.get(entity.direction);
        idleAnimation.play(entity, g);
    }

    private void walk(Entity entity, Graphics g) {
        Animation walkAnimation = walkAnimations.get(entity.direction);
        walkAnimation.play(entity, g);
    }

    private void attack(Entity entity, Graphics g) {

    }

    private void attacked(Entity entity, Graphics g) {
        deadAnimation.play(entity, g);
    }

    private void dead(Entity entity, Graphics g) {
        deadAnimation.play(entity, g);
    }
}
