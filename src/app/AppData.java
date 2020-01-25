package app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AppData {

	private String filename;
	private String resource;

	public AppData(String filename, InputStream stream) throws Exception {
		BufferedReader streamFilter = new BufferedReader(new InputStreamReader(stream));
		String resource = "";
		String line;
		while ((line = streamFilter.readLine()) != null) {
			resource += line + "\n";
		}
		this.resource = resource;
		this.setFilename(filename);
	}

	public AppData(String filename) {
		this.resource = "";
		this.setFilename(filename);
	}

	private void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

	public String toString() {
		return this.resource;
	}

}
