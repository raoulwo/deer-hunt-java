package dev.raoulwo.resource;

import dev.raoulwo.animation.Animation;
import dev.raoulwo.animation.AnimationSprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Supports operations for loading resources like sprites and maybe later on sound effects.
 */
public class Resource {
    /**
     * Loads an image with specified path prefix and name.
     * @param pathPrefix The path prefix for the image.
     * @param file The filename of the image.
     * @return A `BufferedImage` object holding the image data.
     * @throws IOException An exception should the resource stream to the image not successfully open.
     */
    public static BufferedImage loadSprite(String pathPrefix, String file) throws IOException {
        try  (InputStream stream = Resource.class.getResourceAsStream(pathPrefix + file)) {
            if (stream == null) {
                throw new IOException("File not found");
            }
            return ImageIO.read(stream);
        }
    }

    /**
     * Loads a list of images with shared path prefix and unique names.
     * @param pathPrefix The shared path prefix for the images.
     * @param files The image file names.
     * @return A `List<BufferedImage>` holding the image objects.
     * @throws IOException An exception should the resource stream to an image not successfully open.
     */
    public static List<BufferedImage> loadSprites(String pathPrefix, String... files) throws IOException {
        ArrayList<BufferedImage> sprites = new ArrayList<>();
        for (String file : files) {
            sprites.add(loadSprite(pathPrefix, file));
        }
        return sprites;
    }

    public static Animation loadAnimation(int duration, String pathPrefix, String... files) throws IOException {
        List<BufferedImage> sprites = loadSprites(pathPrefix, files);
        List<AnimationSprite> animationSprites = sprites
                .stream()
                .map(sprite -> new AnimationSprite(sprite, duration))
                .toList();
        return new Animation(animationSprites);
    }

    public static String[][] loadMap(String pathPrefix, String file) throws IOException {
        // TODO: Hard-coded map dimensions for now.
        final int ROW = 32;
        final int COL = 44;
        String[][] tileCodes = new String[ROW][COL];

        try (InputStream stream = Resource.class.getResourceAsStream(pathPrefix + file)) {
            if (stream == null) {
                throw new IOException("File not found");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            for (int row = 0; row < ROW; row++) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                String[] codes = line.split("");
                System.arraycopy(codes, 0, tileCodes[row], 0, COL);
            }

            reader.close();
        }

        return tileCodes;
    }

    public static Font loadFont(String pathPrefix, String file) throws IOException, FontFormatException {
        try  (InputStream stream = Resource.class.getResourceAsStream(pathPrefix + file)) {
            if (stream == null) {
                throw new IOException("File not found");
            }
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        }
    }
}
