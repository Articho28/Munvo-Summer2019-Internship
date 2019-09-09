package com.munvo.fileimport.hive.builder;

public interface IHiveImporterBuilder {
	
	public IHiveImporter build();
	public HiveImporterBuilder addPartition(String partitionName);
	public HiveImporterBuilder addJob();
	
}
