package com.munvo.fileimport.bl.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

import java.util.ArrayList;
import java.util.List;

import com.munvo.fileimport.bl.IBusinessLogic;
import com.munvo.fileimport.configuration.AppConfiguration;
import com.munvo.fileimport.configuration.SpringContext;
import com.munvo.fileimport.hive.builder.HiveImporterBuilder;
import com.munvo.fileimport.hive.importer.ImportInformation;
import com.munvo.fileimport.hive.importer.ImporterThread;
import com.munvo.fileimport.request.dto.models.Project;
import com.munvo.fileimport.request.service.IRequestService;
import com.munvo.fileimport.utils.Constants;

/**
 * Method that contains all logic necessary to complete import into hive table
 * @author Artsiom Skliar
 *
 */
public class ImportBL implements IBusinessLogic {
	
	private IRequestService requestService;
	private AppConfiguration appConfig;
	
	
	@Override
	public void init()  {
		requestService = SpringContext.getSpringContext().getRestTemplateContext().getBean(IRequestService.class);
		appConfig = AppConfiguration.getAppConfigInstance();
	}
	/**
	 * Only method accessible inside the business logic. It gets the projects from the database, 
	 * performs matching to determine in which Hive table the file should go, and returns List of
	 * threads ready to be executed concurrently.
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public List<ImporterThread> getImportTasks(WatchKey key) throws IOException {
		List<Project> projects = getAllProjects();
		List<ImporterThread> importTasks = matchFilesToImports(key, projects);
		return importTasks;
	}
	
	/**
	 * Method that will take file names from key and return List of Runnable Objects
	 * Containing information necessary for imports. If not match is found, the file is deleted.
	 * @param key
	 * @param projects
	 * @throws IOException
	 */
	private List<ImporterThread> matchFilesToImports(WatchKey key, List<Project> projects) throws IOException {
		List<ImporterThread> tasks = new ArrayList<>();
		for (WatchEvent<?> event : key.pollEvents()) {
		        WatchEvent<Path> ev = cast(event);
		        String fileName = ev.context().toString();
		        if (ev.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
		        	boolean foundMatch = false;
		        	System.out.println("File " + fileName + " was found");
		        	for (Project p : projects) {
		        		if(fileName.contains(p.getProjectName()) && fileName.contains(p.getProjectCode())) {
		        			foundMatch = true;
		        			ImportInformation importer = buildImporter(p, fileName);
		                	ImporterThread task = new ImporterThread(importer);
		                	tasks.add(task);            	
		        		}
		        	}
		        	if (!foundMatch) {
		        		System.out.println("No found match for file: " + fileName + "\nDeleting now.");
		        		Files.deleteIfExists(Paths.get(appConfig.getProperty(Constants.CONFIG_DIRECTORY_TO_TRACK) + "/" + fileName));
		        	}
		        }
		}
		return tasks;
	}
	
	/**
	 * Builds Importer object containing all information necessary for Hive import.
	 * @param project
	 * @param partitionName
	 * @return
	 */
	private ImportInformation buildImporter(Project project, String partitionName) {
		HiveImporterBuilder builder = new HiveImporterBuilder(project);
		builder.addPartition(partitionName).addJob();
		return builder.build().generateImporter();
	}

	/**
	 * Sends requests to Meta database to get all projects
	 * @return
	 */
	private List<Project> getAllProjects() { 
		List<Project> projects = requestService.getAllProjects();
		return projects;
	}
	
	/**
	 * Util method to obtain file names from events.
	 * reference: https://stackoverflow.com/questions/30284302/watch-directory-using-scheduled-executor-instead-of-hard-loop-in-java
	 * @param <T>
	 * @param event
	 * @return
	 */
	@SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
	
}
