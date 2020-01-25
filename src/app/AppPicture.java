package app;

import java.io.InputStream;

import org.newdawn.slick.opengl.EmptyImageData;
import org.newdawn.slick.Image;

public class AppPicture extends Image {

	private String filename;

	public AppPicture(String filename, InputStream stream) throws Exception {
		super(stream, filename, false);
		this.setFilename(filename);
	}

	public AppPicture(String filename) {
		super(new EmptyImageData(0, 0));
		this.setFilename(filename);
	}

	private void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

}
