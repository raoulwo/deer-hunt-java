package dev.raoulwo.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

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

    public void playMusic(URL url) {
        setFile(url);
        play();
        loop();
    }

    public void stopMusic() {
        stop();
    }

    public void playSound(URL url) {
        setFile(url);
        play();
    }

    public void setFile(URL url) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
