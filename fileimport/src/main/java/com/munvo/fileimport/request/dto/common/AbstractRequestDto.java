package com.munvo.fileimport.request.dto.common;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.munvo.fileimport.configuration.AppConfiguration;
import com.munvo.fileimport.request.dto.models.Job;
import com.munvo.fileimport.request.dto.models.Partition;
import com.munvo.fileimport.request.dto.models.Project;
import com.munvo.fileimport.request.operations.IRequestOperations;
import com.munvo.fileimport.utils.Constants;

public abstract class AbstractRequestDto implements IRequestOperations {
	
	@Autowired
	private RestTemplate restTemplate;
	private AppConfiguration appConfig = AppConfiguration.getAppConfigInstance();
	
	@Override
	public List<Project> getAllProjects() {
	    ResponseEntity<List<Project>> response = restTemplate.exchange(appConfig.getProperty(Constants.REQUEST_GET_PROJECTS_URL), 
	    		HttpMethod.GET, null, 
	    		new ParameterizedTypeReference<List<Project>>() {});
	    List<Project> projects = response.getBody();
	    
	    return projects;
	}
	
	@Override
	public void postPartition(Partition partition) {
	    HttpEntity<Partition> postRequest = new HttpEntity<>(partition);
	    restTemplate.postForObject(appConfig.getProperty(Constants.REQUEST_POST_PARTITION_URL), postRequest, Partition.class);
	}
	
	@Override
	public void markJobReady(Job job) {
		HttpEntity<Job> postJobRequest = new HttpEntity<>(job);
		restTemplate.postForObject(appConfig.getProperty(Constants.REQUEST_POST_JOB_READY_URL), postJobRequest, Job.class);
	}
}

