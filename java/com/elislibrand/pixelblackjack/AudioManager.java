package com.elislibrand.pixelblackjack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioManager
{
    public void play(Audio audio)
    {
        try
        {
            String filePath = "/audio/" + audio.toString().toLowerCase() + ".wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Main.class.getResource(filePath));
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        /*if (clip.getMicrosecondLength() > clip.getMicrosecondPosition())
        {
            
        }*/
    }
}