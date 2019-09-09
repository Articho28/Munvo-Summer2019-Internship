package com.munvo.fileimport.request.dto.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Object used to get projects from database
 * @author Artsiom Skliar
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements IRequest {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("project.name")
	private String projectName;
	
	@JsonProperty("project.code")
	private String projectCode;
	
	@JsonProperty("project.type")
	private String projectType;
	
	@JsonProperty("full.qualified.path")
	private String fullQualifiedPath;
			
	public Project() {}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}


	public String getFullQualifiedPath() {
		return fullQualifiedPath;
	}

	public void setFullQualifiedPath(String fullQualifiedPath) {
		this.fullQualifiedPath = fullQualifiedPath;
	}
	
	@Override
	public String toString() {
		return "ProjectDto{" +
				"id=" + this.id +
				", projectName='" + this.projectName + '\'' +
				", projectCode='" + this.projectCode + '\'' +
				", projectType='" + this.projectType + '\'' +
				'}';
	}

}
