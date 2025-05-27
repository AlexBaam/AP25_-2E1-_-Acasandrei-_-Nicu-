package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameSound {
    public static void play(String soundPath) {
        try {
            File soundFile = new File(soundPath);
            if(!soundFile.exists()) {
                System.out.println("Sound file not found: " + soundPath);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-10.0f); // între -80.0 (foarte încet) și 6.0 (maxim)

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            System.out.println("Failed to play sound: " + e.getMessage());
        }
    }
}
