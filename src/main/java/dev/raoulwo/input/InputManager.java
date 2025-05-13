package dev.raoulwo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A singleton class that manages user input.
 */
public class InputManager implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    private static InputManager INSTANCE;

    private InputManager() {}

    public static InputManager instance() {
        if (INSTANCE == null) {
            INSTANCE = new InputManager();
        }
        return INSTANCE;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    public boolean moveKeyPressed() {
        return upPressed || downPressed || leftPressed || rightPressed;
    }
}
