package dev.raoulwo.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * A singleton class used to handle the game audio.
 */
public class AudioManager {
    public final URL healSound;
    public final URL fightMusic;

    Clip clip;

    private static AudioManager INSTANCE;

    private AudioManager() {
        healSound = getClass().getResource("/audio/sfx/heal.wav");
        fightMusic = getClass().getResource("/audio/music/fight.wav");
    }

    public static AudioManager instance() {
        if (INSTANCE == null) {
            INSTANCE = new AudioManager();
        }
        return INSTANCE;
    }

    /**
     * Plays the given audio track in a loop.
     *
     * @param url The url of the audio track to play.
     */
    public void playMusic(URL url) {
        setFile(url);
        play();
        loop();
    }

    /**
     * Stops the currently playing audio track.
     */
    public void stopMusic() {
        stop();
    }

    /**
     * Plays the given audio track once.
     *
     * @param url The url of the audio track to play.
     */
    public void playSound(URL url) {
        setFile(url);
        play();
    }

    private void setFile(URL url) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void play() {
        clip.start();
    }

    private void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void stop() {
        clip.stop();
    }
}
