package dev.raoulwo;

import dev.raoulwo.graphics.DrawFn;

import javax.swing.*;

/**
 * Acts as an abstraction for the underlying Java graphics layer.
 */
public class Window {

    private final JFrame frame = new JFrame();
    private final Panel panel;

    /**
     * @param drawFn  A callback for rendering the game graphics.
     * @param options Options for creating the window.
     */
    public Window(DrawFn drawFn, WindowOptions options) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Disable resizing for simplicity.
        frame.setTitle(options.title());

        // To render our game, we first need to add our panel to the window.
        panel = new Panel(drawFn, options.width(), options.height());
        frame.add(panel);

        // Resize the frame to fit the size of its subcomponents. In our case, it'll fit the game panel.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Repaints the screen. Should not be called manually since it is called from within the game loop.
     */
    public void repaint() {
        panel.repaint();
    }
}
