package app;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;

public class AppFont extends TrueTypeFont {

	public static final int PLAIN = Font.PLAIN;
	public static final int BOLD = Font.BOLD;
	public static final int ITALIC = Font.ITALIC;

	private String filename;

	public AppFont(String filename, InputStream stream, int type, int size) throws Exception {
		super(Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(type, size), true);
		this.setFilename(filename);
	}

	public AppFont(String filename, int type, int size) {
		super(new Font(null, type, size), true);
		this.setFilename(filename);
	}

	private void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

}
