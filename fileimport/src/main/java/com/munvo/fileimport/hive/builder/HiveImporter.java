package com.munvo.fileimport.hive.builder;

import com.munvo.fileimport.hive.importer.ImportInformation;
import com.munvo.fileimport.request.dto.models.Job;
import com.munvo.fileimport.request.dto.models.Partition;
import com.munvo.fileimport.request.dto.models.Project;

public class HiveImporter implements IHiveImporter {
	
	private Project project;
	private Partition partition;
	private Job job;
	
	
	HiveImporter(HiveImporterBuilder builder) {
		this.project = builder.project;
		this.partition = builder.partition;
		this.job = builder.job;
	}

	@Override
	public ImportInformation generateImporter() {
		ImportInformation importer = new ImportInformation();
		importer.setPartition(this.partition);
		importer.setProject(this.project);
		importer.setJob(this.job);
		return importer;
	}

}
