package com.munvo.fileimport.hive.importer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

import com.munvo.fileimport.configuration.AppConfiguration;
import com.munvo.fileimport.configuration.SpringContext;
import com.munvo.fileimport.hive.connection.HiveConnectionPool;
import com.munvo.fileimport.request.service.IRequestService;
import com.munvo.fileimport.utils.Constants;

/**
 * This class performs the transaction to import into the hive database, record
 * the transaction in the meta database, mark the job as ready, and delete the
 * file once imported. 
 * 
 * @author ArtsiomSkliar
 *
 */
public class ImporterThread implements Runnable {

	private ImportInformation importInformation;
	private AppConfiguration appConfig;
	private IRequestService requestService;
	

	public ImporterThread(ImportInformation importInformation) {
		this.importInformation = importInformation;
		appConfig = AppConfiguration.getAppConfigInstance();
		requestService = SpringContext.getSpringContext().getRestTemplateContext().getBean(IRequestService.class);
	}
	
	@Override
	public void run() {
		importIntoHiveTable(this.importInformation);
		postPartition(this.importInformation);
		markJobReady(this.importInformation);
		deleteFile(this.importInformation);
	}
	
	/**
	 * Method that performs transaction with database.
	 * @param importer
	 */
	private void importIntoHiveTable(ImportInformation importer) {
		try {
			Statement statement = HiveConnectionPool.getHiveConnectionPool().getConnection().createStatement();
			String importCommand = "LOAD DATA LOCAL INPATH '" 
					+ appConfig.getProperty(Constants.CONFIG_DIRECTORY_TO_TRACK)
					+ "/" + importer.getPartition().getPartitionName() 
					+ "' OVERWRITE INTO TABLE campaignqa." 
					+ importer.getProject().getProjectName() + " PARTITION (file_dt='" 
					+ importer.getPartition().getPartitionName() + "')";
			
			statement.execute(importCommand);
			System.out.println("The writing of partition " + importer.getPartition().getPartitionName() + " was successful");
		} catch (SQLException sqlE) {
			System.out.println("Exception : The writing of partition " +importer.getPartition().getPartitionName() + " was unsuccessful due to SQLError");
			sqlE.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception : The writing of partition " + importer.getPartition().getPartitionName() + " was unsuccessful due to an exception");
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that sends partition that is imported into meta database;
	 * @param importer
	 */
	private void postPartition(ImportInformation importInfo) {
		requestService.postPartition(importInfo.getPartition());
	}
	
	/**
	 * Method that sends job information ready
	 * @param importer
	 */
	private void markJobReady(ImportInformation importInfo) {
		requestService.markJobReady(importInfo.getJob());
	}
	
	/**
	 * Method to delete file once import was complete
	 * @param importer
	 */
	private void deleteFile(ImportInformation importInfo) {
		
		//Get file Path
		String fileToDeletePath = appConfig.getProperty(
				Constants.CONFIG_DIRECTORY_TO_TRACK) 
				+ "/" 
				+ importInfo.getPartition().getPartitionName();
		try {
			Files.deleteIfExists(Paths.get(fileToDeletePath));
			System.out.println("File " + fileToDeletePath + " was deleted succesfully");
		} catch (IOException e) {
			System.out.println("IO Error occured while trying to delete file" + fileToDeletePath);
			e.printStackTrace();
		};
	}
}
