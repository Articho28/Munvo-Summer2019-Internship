package com.munvo.fileimport.request.operations;

import java.util.List;

import com.munvo.fileimport.request.dto.models.Job;
import com.munvo.fileimport.request.dto.models.Partition;
import com.munvo.fileimport.request.dto.models.Project;

/**
 * CRUD Operations for fileimport module
 * @author Artsiom Skliar
 *
 */
public interface IRequestOperations {

	/**
	 * Gets all recorded projects in the CampaignQA database
	 * @return
	 */
	public List<Project> getAllProjects();
	
	/**
	 * Posts partition after it was imported in the hive table related to a project
	 * @param partition
	 */
	public void postPartition(Partition partition);
	
	/**
	 * Marks a job as ready once the data has been imported
	 * @param job
	 */
	public void markJobReady(Job job);
}
