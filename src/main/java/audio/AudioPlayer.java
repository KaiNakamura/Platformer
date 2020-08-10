package main.java.audio;

import main.java.Constants.File;

import java.util.ArrayList;

public class AudioPlayer {
	private ArrayList<Audio> audio;

	public AudioPlayer() {
		audio = new ArrayList<>();	
	}

	public void update() {
		for (int i = 0; i < audio.size(); i++) {
			if (audio.get(i).hasStopped()) {
				audio.remove(i);
			}
		}
	}

	public void play(Audio audio) {
		this.audio.add(audio);
		audio.play();
	}

	public void stop(File file) {
		for (int i = 0; i < audio.size(); i++) {
			if (audio.get(i).getFile() == file) {
				audio.get(i).stop();
				audio.remove(i);
			}
		}
	}
}
