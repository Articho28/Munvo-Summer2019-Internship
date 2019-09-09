package com.munvo.fileimport.watcherservice;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.munvo.fileimport.bl.common.BusinessLogicFactory;
import com.munvo.fileimport.bl.common.ImportBL;
import com.munvo.fileimport.hive.importer.ImporterThread;

/**
 * 
 * @author Artsiom Skliar
 * reference: https://stackoverflow.com/questions/30284302/watch-directory-using-scheduled-executor-instead-of-hard-loop-in-java
 */
public class DirectoryWatcher implements IDirectoryWatcher {
	
	private final Path dir;
	private final WatchService watcher;
	private final WatchKey key;
	private ThreadPoolExecutor executor;

	@SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
	
	public DirectoryWatcher(Path dir, int numOfThreads) throws IOException {
        this.dir = dir;
        this.watcher = FileSystems.getDefault().newWatchService();
        this.key = dir.register(watcher, 
        		StandardWatchEventKinds.ENTRY_CREATE 
        		);
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numOfThreads);
    }
	
	/**
	 * Main logic of the application
	 * This method continuously monitors a directory for new files.
	 * When a new file (or batch of files) come(s) in, it sends it over to the business logic
	 * to determine the proper HIVE table in the Hadoop cluster in which to import the new data.
	 */
	@Override
	public void execute() throws IOException {
        try {
            for (;;) {
            	
                // wait for key to be signaled
                WatchKey key = watcher.take();

                if (this.key != key) {
                    System.err.println("WatchKey not recognized!");
                    continue;
                }
                
               List<ImporterThread> importTasks = BusinessLogicFactory.getBusinessLogic(ImportBL.class).getImportTasks(key);
               
               for(ImporterThread task : importTasks) {
            	   executor.execute(task);
               }
               
                // reset key
                if (!key.reset()) {
                    break;
                }
            }
        } catch (InterruptedException x) {
            return;
        }
    }
}
