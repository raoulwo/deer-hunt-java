package dev.raoulwo;

import java.net.URL;
import dev.raoulwo.graphics.DrawFn;
import dev.raoulwo.input.InputManager;
import dev.raoulwo.tile.TileManager;

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

    // TODO: Clean these up
    public AudioManager audio = new AudioManager();
    public TileManager tileManager = new TileManager(this);
//    public Item item = new Heart(this);

    // TODO: Created this constructor just to make the host panel compile.
    public Panel() {
        this.drawFn = null;
    }

    /**
     *
     * @param drawFn A callback for rendering the game graphics.
     * @param width The panel width.
     * @param height The panel height
     */
    public Panel(DrawFn drawFn, int width, int height) {
        this.drawFn = drawFn;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        // Allows the game panel to be focused in order to receive keyboard inputs.
        setFocusable(true);

        addKeyListener(InputManager.instance());

//        playMusic(audio.fightMusic);
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

        // The graphics object needs to be disposed after use to save some memory.
        g2d.dispose();
    }

    // TODO: Move the music logic out of this class.
    public void playMusic(URL url) {
        audio.setFile(url);
        audio.play();
        audio.loop();
    }

    public void stopMusic() {
        audio.stop();
    }

    public void playSound(URL url) {
        audio.setFile(url);
        audio.play();
    }

}
