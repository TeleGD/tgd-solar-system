package app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.EmptyImageData;

public class AppLoader {

	private static Font defaultFont;
	private static Map <String, Map <Integer, Map <Integer, Font>>> fonts;
	private static Image defaultImage;
	private static Map <String, Image> images;

	static {
		AppLoader.defaultFont = new TrueTypeFont (new java.awt.Font (null, java.awt.Font.PLAIN, 16), true);
		AppLoader.fonts = new HashMap <String, Map <Integer, Map <Integer, Font>>> ();
		AppLoader.defaultImage = new Image (new EmptyImageData (0, 0));
		AppLoader.images = new HashMap <String, Image> ();
	}

	public static Font loadFont (String filename, int type, int size) {
		Map <Integer, Map <Integer, Font>> types;
		if ((types = AppLoader.fonts.get (filename)) == null) {
			types = new HashMap <Integer, Map <Integer, Font>> ();
			AppLoader.fonts.put (filename, types);
		}
		Map <Integer, Font> sizes;
		if ((sizes = types.get (type)) == null) {
			sizes = new HashMap <Integer, Font> ();
			types.put (type, sizes);
		}
		Font resource = sizes.get (size);
		if (resource == null) {
			try {
				try {
					InputStream stream = new FileInputStream (System.class.getResource (filename).getPath ());
					try {
						try {
							resource = new TrueTypeFont (java.awt.Font.createFont (java.awt.Font.TRUETYPE_FONT, stream).deriveFont (type, size), true);
						} catch (java.awt.FontFormatException error) {
							resource = AppLoader.defaultFont;
						}
					} catch (IOException error) {
						resource = AppLoader.defaultFont;
					}
					stream.close ();
				} catch (NullPointerException error) {
					resource = AppLoader.defaultFont;
				}
			} catch (IOException error) {
				resource = AppLoader.defaultFont;
			}
			sizes.put (size, resource);
		}
		return resource;
	}

	public static Image loadImage (String filename) {
		Image resource = AppLoader.images.get (filename);
		if (resource == null) {
			if (filename != null && filename.startsWith ("/")) {
				try {
					try {
						InputStream stream = new FileInputStream (System.class.getResource (filename).getPath ());
						try {
							resource = new Image (stream, filename, false);
						} catch (SlickException exception) {
							resource = AppLoader.defaultImage;
						}
						stream.close ();
					} catch (NullPointerException error) {
						resource = AppLoader.defaultImage;
					}
				} catch (IOException error) {
					resource = AppLoader.defaultImage;
				}
			} else {
				resource = AppLoader.defaultImage;
			}
			AppLoader.images.put (filename, resource);
		}
		return resource;
	}

}
