package com.fajar.imagemosaic.config;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ConfigLoader {

	static ConfigLoader loader = null;
	private Properties properties = new Properties();

	public static ConfigLoader instance() {
		if (loader == null) {
			loader = new ConfigLoader();
		}
		return loader;
	}
	private int loadInt(String key) {
		return Integer.valueOf(properties.getProperty(key));
	}
	public int getRandomMinSize() {
		return  loadInt("image.random.minSize");
	}
	public int getRandomAdjustedSize() {
		return  loadInt("image.random.adjustedSize");
	}
	public int getInputScaleSize() {
		return loadInt("image.input.scaleSize");
	}
	public String getRandomRawImagePath() {
		return properties.getProperty("image.random.rawPath");
	}
	public String getRandomImagePath() {
		return properties.getProperty("image.random.path");
	}
	public String getOutputPath() {
		return properties.getProperty("image.output.path");
	}
	public String getInputPath() {
		return properties.getProperty("image.input.path");
	}

	private ConfigLoader() {
		loadConfig();
	}

	public void loadConfig() {

		URL uri = getClass().getClassLoader().getResource("config.properties");
		Properties prop = new Properties();
		try {
			prop.load(uri.openStream());
			this.properties = prop;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
