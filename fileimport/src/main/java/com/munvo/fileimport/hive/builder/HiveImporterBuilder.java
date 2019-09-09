package com.munvo.fileimport.hive.builder;

import com.munvo.fileimport.hive.builder.HiveImporter;
import com.munvo.fileimport.request.dto.models.Job;
import com.munvo.fileimport.request.dto.models.Partition;
import com.munvo.fileimport.request.dto.models.Project;

public class HiveImporterBuilder implements IHiveImporterBuilder {
	
	protected Project project;
	protected Partition partition;
	protected Job job;
	
	public HiveImporterBuilder(Project project) {
		this.project = project;
	}
	
	public HiveImporterBuilder addPartition(String partitionName) {
		partition = new Partition();
		partition.setPartitionName(partitionName);
		partition.setProject(project);
		return this;
	}
	
	public HiveImporterBuilder addJob() {
		this.job = new Job(this.project.getProjectName());
		return this; 
	}
	
	public IHiveImporter build()  { 
		return new HiveImporter(this);
	}
	
}
