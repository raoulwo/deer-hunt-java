package dev.raoulwo;

import dev.raoulwo.graphics.DrawFn;
import dev.raoulwo.input.InputManager;

import javax.swing.*;
import java.awt.*;

/**
 * The panel is the component used for rendering our game's graphical content.
 */
public class Panel extends JPanel {

    /**
     * Callback passed from the main game class so that we can define graphics calls there.
     */
    private final DrawFn drawFn;

    public Panel() {
        this.drawFn = null;
    }

    /**
     * @param drawFn A callback for rendering the game graphics.
     * @param width The panel width.
     * @param height The panel height
     */
    public Panel(DrawFn drawFn, int width, int height) {
        this.drawFn = drawFn;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        // Allows the game panel to be focused to receive keyboard inputs.
        setFocusable(true);
        addKeyListener(InputManager.instance());
        addMouseListener(InputManager.instance());
        addMouseMotionListener(InputManager.instance());
    }

    /**
     * Renders the graphical components to the game panel. This method will not be called directly by the game loop
     * each frame, instead the method `repaint` will be called from within the loop.
     */
    @Override
    public void paintComponent(Graphics g) {
        // Paints the game panel component using a utility `Graphics` object.
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        drawFn.draw(new dev.raoulwo.graphics.Graphics(g2d));

        // The graphics object needs to be disposed of after use to save some memory.
        g2d.dispose();
    }

}
