package com.munvo.fileimport.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AppConfiguration {
	
	private static Properties props;
	private static AppConfiguration appConfigInstance;
	
	private AppConfiguration() {
		props = new Properties();
		try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
			props.load(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static AppConfiguration getAppConfigInstance() {
		if (appConfigInstance == null) {
			appConfigInstance = new AppConfiguration();
		}
		return appConfigInstance;
	}
	
	public String getProperty(String key) {
		return this.props.getProperty(key);
	}

}
