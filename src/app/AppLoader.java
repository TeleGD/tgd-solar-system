package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class AppLoader {

	private static Map <String, Map <Integer, Map <Integer, AppFont>>> fontList;
	private static Map <String, AppPicture> pictureList;
	private static Map <String, AppAudio> audioList;

	static {
		AppLoader.fontList = new HashMap <String, Map <Integer, Map <Integer, AppFont>>> ();
		AppLoader.pictureList = new HashMap <String, AppPicture> ();
		AppLoader.audioList = new HashMap <String, AppAudio> ();
		SoundStore.get ().init ();
	}

	private static InputStream openStream (String filename) {
		InputStream stream = null;
		if (filename != null && filename.startsWith ("/")) {
			try {
				stream = new FileInputStream (System.class.getResource (filename).getPath ());
			} catch (Exception error) {}
			if (stream == null) {
				try {
					stream = ResourceLoader.getResourceAsStream (filename.replaceAll ("/+", "/").substring (1).replace ("/", File.separator));
				} catch (Exception error) {}
			}
		}
		return stream;
	}

	private static void closeStream (InputStream stream) {
		try {
			stream.close ();
		} catch (Exception error) {}
	}

	public static AppFont loadFont (String filename, int type, int size) {
		Map <Integer, Map <Integer, AppFont>> types = null;
		if ((types = AppLoader.fontList.get (filename)) == null) {
			types = new HashMap <Integer, Map <Integer, AppFont>> ();
			AppLoader.fontList.put (filename, types);
		}
		Map <Integer, AppFont> sizes = null;
		if ((sizes = types.get (type)) == null) {
			sizes = new HashMap <Integer, AppFont> ();
			types.put (type, sizes);
		}
		AppFont resource = sizes.get (size);
		if (resource == null) {
			InputStream stream = AppLoader.openStream (filename);
			if (stream != null) {
				try {
					resource = new AppFont (filename, stream, type, size);
				} catch (Exception error) {}
				AppLoader.closeStream (stream);
			}
			if (resource == null) {
				resource = new AppFont (filename, type, size);
			}
			sizes.put (size, resource);
		}
		return resource;
	}

	public static AppPicture loadPicture (String filename) {
		AppPicture resource = AppLoader.pictureList.get (filename);
		if (resource == null) {
			InputStream stream = AppLoader.openStream (filename);
			if (stream != null) {
				try {
					resource = new AppPicture (filename, stream);
				} catch (Exception error) {}
				AppLoader.closeStream (stream);
			}
			if (resource == null) {
				resource = new AppPicture (filename);
			}
			AppLoader.pictureList.put (filename, resource);
		}
		return resource;
	}

	public static AppAudio loadAudio (String filename) {
		AppAudio resource = AppLoader.audioList.get (filename);
		if (resource == null) {
			InputStream stream = AppLoader.openStream (filename);
			if (stream != null) {
				try {
					resource = new AppAudio (filename, stream);
				} catch (Exception error) {}
				AppLoader.closeStream (stream);
			}
			if (resource == null) {
				resource = new AppAudio (filename);
			}
			AppLoader.audioList.put (filename, resource);
		}
		return resource;
	}

}
