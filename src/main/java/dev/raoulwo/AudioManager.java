package dev.raoulwo;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class AudioManager {
    public final URL healSound;
    public final URL fightMusic;

    Clip clip;

    public AudioManager() {
        healSound = getClass().getResource("/audio/sfx/heal.wav");
        fightMusic = getClass().getResource("/audio/music/fight.wav");
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
