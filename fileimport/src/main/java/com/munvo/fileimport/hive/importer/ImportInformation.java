package com.munvo.fileimport.hive.importer;

import com.munvo.fileimport.request.dto.models.Job;
import com.munvo.fileimport.request.dto.models.Partition;
import com.munvo.fileimport.request.dto.models.Project;

/**
 * 
 * @author Artsiom Skliar
 *
 */
public class ImportInformation {
	
	private Project project;
	private Partition partition;
	private Job job;
		
	public void setProject(Project project) {
		this.project = project;
	}
	
	public Project getProject() {
		return this.project;
	}
	
	public void setPartition(Partition partition) {
		this.partition = partition;
	}
	
	public Partition getPartition() {
		return this.partition;
	}
	
	public Job getJob() {
		return this.job;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	
}
