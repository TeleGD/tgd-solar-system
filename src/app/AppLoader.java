package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class AppLoader {

	static private String home;

	static {
		AppLoader.home = System.getProperty("user.home") + File.separator + ".tgd";
	}

	private static Map<String, Map<Integer, Map<Integer, AppFont>>> fontList;
	private static Map<String, AppPicture> pictureList;
	private static Map<String, AppAudio> audioList;
	private static Map<String, AppIcon> iconList;
	private static Map<String, AppData> dataList;

	static {
		AppLoader.fontList = new HashMap<String, Map<Integer, Map<Integer, AppFont>>>();
		AppLoader.pictureList = new HashMap<String, AppPicture>();
		AppLoader.audioList = new HashMap<String, AppAudio>();
		AppLoader.iconList = new HashMap<String, AppIcon>();
		AppLoader.dataList = new HashMap<String, AppData>();
		SoundStore.get().init();
	}

	public static String resolve(String filename) {
		InputStream stream = null;
		String path = null;
		if (filename != null && filename.startsWith("/")) {
			try {
				path = System.class.getResource(filename).getPath();
				stream = new FileInputStream(path);
				stream.close();
			} catch (Exception error) {
				path = null;
			}
			if (path == null) {
				try {
					path = filename.replaceAll("/+", "/").substring(1).replace("/", File.separator);
					stream = ResourceLoader.getResourceAsStream(path);
					stream.close();
				} catch (Exception error) {
					path = null;
				}
			}
		}
		return path;
	}

	private static InputStream openStream(String filename) {
		InputStream stream = null;
		if (filename != null && filename.startsWith("/")) {
			try {
				stream = new FileInputStream(System.class.getResource(filename).getPath());
			} catch (Exception error) {}
			if (stream == null) {
				try {
					stream = ResourceLoader.getResourceAsStream(filename.replaceAll("/+", "/").substring(1).replace("/", File.separator));
				} catch (Exception error) {}
			}
		}
		return stream;
	}

	private static void closeStream(InputStream stream) {
		try {
			stream.close();
		} catch (Exception error) {}
	}

	public static AppFont loadFont(String filename, int type, int size) {
		Map<Integer, Map<Integer, AppFont>> types = null;
		if ((types = AppLoader.fontList.get(filename)) == null) {
			types = new HashMap<Integer, Map<Integer, AppFont>>();
			AppLoader.fontList.put(filename, types);
		}
		Map<Integer, AppFont> sizes = null;
		if ((sizes = types.get(type)) == null) {
			sizes = new HashMap<Integer, AppFont>();
			types.put(type, sizes);
		}
		AppFont resource = sizes.get(size);
		if (resource == null) {
			InputStream stream = AppLoader.openStream(filename);
			if (stream != null) {
				try {
					resource = new AppFont(filename, stream, type, size);
				} catch (Exception error) {}
				AppLoader.closeStream(stream);
			}
			if (resource == null) {
				resource = new AppFont(filename, type, size);
			}
			sizes.put(size, resource);
		}
		return resource;
	}

	public static AppPicture loadPicture(String filename) {
		AppPicture resource = AppLoader.pictureList.get(filename);
		if (resource == null) {
			InputStream stream = AppLoader.openStream(filename);
			if (stream != null) {
				try {
					resource = new AppPicture(filename, stream);
				} catch (Exception error) {}
				AppLoader.closeStream(stream);
			}
			if (resource == null) {
				resource = new AppPicture(filename);
			}
			AppLoader.pictureList.put(filename, resource);
		}
		return resource;
	}

	public static AppAudio loadAudio(String filename) {
		AppAudio resource = AppLoader.audioList.get(filename);
		if (resource == null) {
			InputStream stream = AppLoader.openStream(filename);
			if (stream != null) {
				try {
					resource = new AppAudio(filename, stream);
				} catch (Exception error) {}
				AppLoader.closeStream(stream);
			}
			if (resource == null) {
				resource = new AppAudio(filename);
			}
			AppLoader.audioList.put(filename, resource);
		}
		return resource;
	}

	public static AppIcon loadIcon(String filename) {
		AppIcon resource = AppLoader.iconList.get(filename);
		if (resource == null) {
			InputStream stream = AppLoader.openStream(filename);
			if (stream != null) {
				try {
					resource = new AppIcon(filename, stream);
				} catch (Exception error) {}
				AppLoader.closeStream(stream);
			}
			if (resource == null) {
				resource = new AppIcon(filename);
			}
			AppLoader.iconList.put(filename, resource);
		}
		return resource;
	}

	public static String loadData(String filename) {
		AppData resource = AppLoader.dataList.get(filename);
		if (resource == null) {
			InputStream stream = AppLoader.openStream(filename);
			if (stream != null) {
				try {
					resource = new AppData(filename, stream);
				} catch (Exception error) {}
				AppLoader.closeStream(stream);
			}
			if (resource == null) {
				resource = new AppData(filename);
			}
			AppLoader.dataList.put(filename, resource);
		}
		return resource.toString();
	}

	public static String restoreData(String filename) {
		String data = "";
		if (filename == null || !filename.startsWith("/")) {
			return data;
		}
		filename = AppLoader.home + filename.replaceAll("/+", "/").replace("/", File.separator);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			data = "";
			String line;
			while ((line = reader.readLine()) != null) {
				data += line + "\n";
			}
			reader.close();
		} catch (Exception error) {}
		return data;
	}

	public static void saveData(String filename, String data) {
		if (filename == null || !filename.startsWith("/")) {
			return;
		}
		filename = filename.replaceAll("/+", "/");
		new File(AppLoader.home + File.separator + filename.substring(1, filename.lastIndexOf("/")).replace("/", File.separator)).mkdirs();
		filename = AppLoader.home + filename.replace("/", File.separator);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			if (data == null) {
				data = "";
			} else if (data.length() != 0 && !data.endsWith("\n")) {
				data += "\n";
			}
			writer.write(data);
			writer.close();
		} catch (Exception error) {}
	}

}
