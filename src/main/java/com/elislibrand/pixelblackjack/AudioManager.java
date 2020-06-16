package com.elislibrand.pixelblackjack;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager
{
    public void play(Audio audio)
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Main.class.getResource("/audio/" + audio.toString().toLowerCase() + ".wav"));
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (UnsupportedAudioFileException e)
        {
            System.out.println("UnsupportedAudioFileException when loading audio!");
        }
        catch (LineUnavailableException e)
        {
            System.out.println("LineUnavailableException when loading audio!");
        }
        catch (IOException e)
        {
            System.out.println("IOException when loading audio!");
        }
    }
}