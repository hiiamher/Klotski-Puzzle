package music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private Clip clip;

    public Music(String filename) {
        try {
            File file = new File(filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

            AudioFormat baseFormat = audioInputStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

            AudioInputStream convertedAudioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);


            clip = AudioSystem.getClip();
            clip.open(convertedAudioInputStream);

            convertedAudioInputStream.close();
            audioInputStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip!= null) {
            clip.start();
        }
    }

    public void stop() {
        if (clip!= null) {
            clip.stop();
        }
    }

    public void loop() {
        if (clip!= null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public boolean isPlaying() {
        if (clip!= null) {
            return clip.isRunning();
        }
        return false;
    }

    public void setVolume(float volume) {
        if (clip!= null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
    }
}


