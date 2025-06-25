package dev.raoulwo;

/**
 * Functions as the main entry point of the game.
 */
public class EntryPoint {
    public static void main(String[] args) {
        Game game = new Game();
        game.startThread();
    }
}
