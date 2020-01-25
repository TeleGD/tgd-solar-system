package app;

import java.io.InputStream;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.NullAudio;
import org.newdawn.slick.openal.SoundStore;

public class AppAudio implements Audio {

	private String filename;
	private Audio resource;

	public AppAudio(String filename, InputStream stream) throws Exception {
		try {
			this.resource = SoundStore.get().getOgg(stream);
		} catch (Exception error) {}
		if (this.resource == null) {
			try {
				this.resource = SoundStore.get().getWAV(stream);
			} catch (Exception error) {}
		}
		if (this.resource == null) {
			try {
				this.resource = SoundStore.get().getAIF(stream);
			} catch (Exception error) {}
		}
		if (this.resource == null) {
			this.resource = SoundStore.get().getMOD(stream);
		}
		this.setFilename(filename);
	}

	public AppAudio(String filename) {
		this.resource = new NullAudio();
		this.setFilename(filename);
	}

	private void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

	public int getBufferID() {
		return this.resource.getBufferID();
	}

	public float getPosition() {
		return this.resource.getPosition();
	}

	public boolean isPlaying() {
		return this.resource.isPlaying();
	}

	public int playAsMusic(float pitch, float gain, boolean loop) {
		return this.resource.playAsMusic(pitch, gain, loop);
	}

	public int playAsSoundEffect(float pitch, float gain, boolean loop) {
		return this.resource.playAsSoundEffect(pitch, gain, loop);
	}

	public int playAsSoundEffect(float pitch, float gain, boolean loop, float x, float y, float z) {
		return this.resource.playAsSoundEffect(pitch, gain, loop, x, y, z);
	}

	public boolean setPosition(float position) {
		return this.resource.setPosition(position);
	}

	public void stop() {
		this.resource.stop();
	}

}
