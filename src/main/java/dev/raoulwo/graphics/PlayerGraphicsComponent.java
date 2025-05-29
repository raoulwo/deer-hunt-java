package dev.raoulwo.graphics;

import dev.raoulwo.animation.Animation;
import dev.raoulwo.entity.Direction;
import dev.raoulwo.entity.Entity;
import dev.raoulwo.resource.Resource;

import java.io.IOException;
import java.util.HashMap;

public class PlayerGraphicsComponent implements GraphicsComponent {

    private static final int IDLE_ANIM_FRAME_DURATION = 1;
    private static final int ATTACK_ANIM_FRAME_DURATION = 1;
    private static final int WALK_ANIM_FRAME_DURATION = 15;
    private static final int DEAD_ANIM_FRAME_DURATION = 1;

    private final HashMap<Direction, Animation> idleAnimations = new HashMap<>();
    private final HashMap<Direction, Animation> attackAnimations = new HashMap<>();
    private final HashMap<Direction, Animation> walkAnimations = new HashMap<>();
    private Animation deadAnimation;

    private final Camera camera = Camera.instance();

    private boolean centerCamera;

    public PlayerGraphicsComponent(String name, boolean centerCamera) {
        this.centerCamera = centerCamera;
        try {
            initializeAnimations(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeAnimations(String name) throws IOException {

        String idlePath = "/sprites/players/ninja_" + name + "/idle/";
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

        String attackPath = "/sprites/players/ninja_" + name + "/attack/";
        Animation attackSouthAnimation = Resource.loadAnimation(
                ATTACK_ANIM_FRAME_DURATION,
                attackPath,
                "attack_south.png");
        attackAnimations.put(Direction.DOWN, attackSouthAnimation);
        Animation attackNorthAnimation = Resource.loadAnimation(
                ATTACK_ANIM_FRAME_DURATION,
                attackPath,
                "attack_north.png");
        attackAnimations.put(Direction.UP, attackNorthAnimation);
        Animation attackWestAnimation = Resource.loadAnimation(
                ATTACK_ANIM_FRAME_DURATION,
                attackPath,
                "attack_west.png");
        attackAnimations.put(Direction.LEFT, attackWestAnimation);
        Animation attackEastAnimation = Resource.loadAnimation(
                ATTACK_ANIM_FRAME_DURATION,
                attackPath,
                "attack_east.png");
        attackAnimations.put(Direction.RIGHT, attackEastAnimation);

        String deadPath = "/sprites/players/ninja_" + name + "/dead/";
        deadAnimation = Resource.loadAnimation(
                DEAD_ANIM_FRAME_DURATION,
                deadPath,
                "dead.png");

        String walkPath = "/sprites/players/ninja_" + name + "/walk/";
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
    }

    @Override
    public void draw(Entity entity, Graphics g) {
        if (!centerCamera) {
            entity.screenX = entity.x - camera.x + Camera.SCREEN_X;
            entity.screenY = entity.y - camera.y + Camera.SCREEN_Y;
        } else {
            camera.centerOn(entity.x, entity.y);
        }

        switch (entity.state) {
            case IDLE -> idle(entity, g);
            case WALK -> walk(entity, g);
            case ATTACK -> attack(entity, g);
            case ATTACKED -> attacked(entity, g);
        }
    }

    private void idle(Entity entity, Graphics g) {
        Animation idle = idleAnimations.get(entity.direction);
        idle.play(entity, g);
    }

    private void walk(Entity entity, Graphics g) {
        Animation walk = walkAnimations.get(entity.direction);
        walk.play(entity, g);
    }

    private void attack(Entity entity, Graphics g) {
        Animation attack = attackAnimations.get(entity.direction);
        attack.play(entity, g);
    }

    private void attacked(Entity entity, Graphics g) {
        Animation attacked = deadAnimation;
        attacked.play(entity, g);
    }
}

