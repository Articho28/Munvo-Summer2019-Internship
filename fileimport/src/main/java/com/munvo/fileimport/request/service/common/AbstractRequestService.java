package com.munvo.fileimport.request.service.common;

import java.util.List;

import com.munvo.fileimport.request.dto.models.Job;
import com.munvo.fileimport.request.dto.models.Partition;
import com.munvo.fileimport.request.dto.models.Project;
import com.munvo.fileimport.request.operations.IRequestOperations;

public abstract class AbstractRequestService implements IRequestOperations {
	
	protected abstract IRequestOperations getRequestDto();
	
	@Override
	public List<Project> getAllProjects() {
		return getRequestDto().getAllProjects();
	}
	
	@Override
	public void postPartition(Partition partition) {
		getRequestDto().postPartition(partition);
	}
	
	@Override
	public void markJobReady(Job job) {
		getRequestDto().markJobReady(job);
	}
	
}
