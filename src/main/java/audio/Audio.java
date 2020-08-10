package main.java.audio;

import main.java.Constants.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Audio {
	private Clip clip;
	private File file;
	private boolean loop;
	private boolean hasStopped;

	public Audio(File file, double volume, boolean loop) {
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file.getFile()));
			clip.addLineListener(new LineListener(){
				@Override
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP) {
						hasStopped = true;
					}
				}
			});

			this.file = file;
			setVolume(volume);
			this.loop = loop;
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
	
	public void stop() {
		clip.stop();
	}

	public void setVolume(double volume) {
		FloatControl gainControl = (FloatControl) clip.getControl(
			FloatControl.Type.MASTER_GAIN
		);

		float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}

	public File getFile() {
		return file;
	}

	public boolean hasStopped() {
		return hasStopped;
	}
}
