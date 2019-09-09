package com.munvo.fileimport;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.munvo.fileimport.configuration.AppConfiguration;
import com.munvo.fileimport.utils.Constants;
import com.munvo.fileimport.watcherservice.DirectoryWatcherFactory;

public class Application {

	public static void main(String[] args) throws IOException {
		System.out.println("Jar started...");
		AppConfiguration appConfig = AppConfiguration.getAppConfigInstance();
	   	Path dir = Paths.get(appConfig.getProperty(Constants.CONFIG_DIRECTORY_TO_TRACK));
	   	int configuredThreadPoolSize = Integer.parseInt(appConfig.getProperty(Constants.THREAD_POOL_SIZE));
	   	DirectoryWatcherFactory.getDirectoryWatcher(dir, configuredThreadPoolSize).execute();
	}

}
