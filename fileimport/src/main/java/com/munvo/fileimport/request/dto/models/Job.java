package com.munvo.fileimport.request.dto.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object used to mark jobs ready in CampaignQA database
 * @author Artsiom Skliar
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job implements IRequest {
	
	@JsonProperty(value = "project.name")
	private String projectName;
	
	public Job(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String name) {
		this.projectName = name;
	}
}
