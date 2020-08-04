package main.java.audio;

import main.java.Constants.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
	private Clip clip;
	private boolean loop;

    public Audio(File file, double volume, boolean loop) {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file.getFile()));
			this.loop = loop;
			setVolume(volume);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public Audio(File file, boolean loop) {
		this(file, 1.0, loop);
	}

	public Audio(File file, double volume) {
		this(file, volume, false);
	}
	
	public Audio(File file) {
		this(file, 1.0, false);
	}

    public void play() {
		// If running, stop
		if (clip.isRunning()) {
			clip.stop();
		}
		// Rewind
		clip.setFramePosition(0);

		if (loop) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			clip.start();
		}
    }

	public void setVolume(double volume) {
		FloatControl gainControl = (FloatControl) clip.getControl(
			FloatControl.Type.MASTER_GAIN
		);

		float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}

	public boolean isRunning() {
		return clip.isRunning();
	}
}
