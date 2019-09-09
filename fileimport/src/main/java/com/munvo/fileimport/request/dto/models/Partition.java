package com.munvo.fileimport.request.dto.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
 * Object used to post partitions related to projects in CampaignQA database
 * @author Artsiom Skliar
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Partition implements IRequest {
		
	@JsonProperty(value = "partition.name")
	private String partitionName;
	
	@JsonProperty(value = "project")
	private Project project; 
	
	public Partition() {}


	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}
	
	public Project getProject() {
		return this.project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	@Override
	public String toString() {
		return "PartitionDto{" +
				"partitionName='" + this.partitionName + '\'' +
				", project=" + this.project.getId() + 
				'}';
	}

}
