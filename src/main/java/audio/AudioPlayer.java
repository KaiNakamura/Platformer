package main.java.audio;

import java.util.ArrayList;

public class AudioPlayer {
	private ArrayList<Audio> audio;

	public AudioPlayer() {
		audio = new ArrayList<>();	
	}

	public void update() {
		for(int i = 0; i < audio.size(); i++) {
			if (!audio.get(i).isRunning()) {
				audio.remove(i);
			}
		}
	}

	public void play(Audio audio) {
		this.audio.add(audio);
		audio.play();
	}
}
