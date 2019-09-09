package com.munvo.fileimport.watcherservice;

import java.io.IOException;
import java.nio.file.Path;

public class DirectoryWatcherFactory {
	
	public static IDirectoryWatcher getDirectoryWatcher(Path directory, int numberOfThreads) throws IOException {
		return new DirectoryWatcher(directory, numberOfThreads);
	}

}
