package br.com.ihm.marianacvn.utils;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

public class MusicPlayer {
    private MediaPlayer mediaPlayer;

    public MusicPlayer(String mp3FilePath) {
        // Initialize JavaFX
        new JFXPanel();

        URL resource = MusicPlayer.class.getResource(mp3FilePath);
        assert resource != null;
        Media media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);

        Object volumeConfigValue = Config.getConfigValue("volume");
        double volume;
        if (volumeConfigValue instanceof Integer) {
            volume = ((Integer) volumeConfigValue).doubleValue();
        } else if (volumeConfigValue instanceof BigDecimal) {
            volume = ((BigDecimal) volumeConfigValue).doubleValue();
        } else {
            throw new IllegalArgumentException("Invalid volume value");
        }
        setVolume(volume);

        Object activeMusicConfigValue = Config.getConfigValue("music");
        boolean activeMusic;
        if (activeMusicConfigValue instanceof Boolean) {
            activeMusic = (Boolean) activeMusicConfigValue;
        } else {
            throw new IllegalArgumentException("Invalid music value");
        }

        if (activeMusic) {
            playInLoop();
        }

    }

    public void play() {
        mediaPlayer.play();
        Config.setConfigValueAndSave("music", true);
    }

    public void stop() {
        mediaPlayer.stop();
        Config.setConfigValueAndSave("music", false);
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
        Config.setConfigValueAndSave("volume", volume);
    }

    public double getVolume() {
        return mediaPlayer.getVolume();
    }

    public void playInLoop() {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        Config.setConfigValueAndSave("music", true);
    }

}