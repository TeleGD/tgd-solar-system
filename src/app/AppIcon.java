package app;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class AppIcon extends ImageIcon {

	private static final long serialVersionUID = 0;

	private String filename;

	public AppIcon(String filename, InputStream stream) throws Exception {
		super(ImageIO.read(stream));
		this.setFilename(filename);
	}

	public AppIcon(String filename) {
		super(new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR_PRE));
		this.setFilename(filename);
	}

	private void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

}
