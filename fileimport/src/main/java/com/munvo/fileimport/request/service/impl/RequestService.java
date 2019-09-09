package com.munvo.fileimport.request.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.munvo.fileimport.request.dto.IRequestDto;
import com.munvo.fileimport.request.operations.IRequestOperations;
import com.munvo.fileimport.request.service.IRequestService;
import com.munvo.fileimport.request.service.common.AbstractRequestService;

@Service
public class RequestService extends AbstractRequestService implements IRequestService  {

	@Autowired
	private IRequestDto requestDto;
	
	public RequestService() {
		super();
	}

	@Override
	protected IRequestOperations getRequestDto() {
		return requestDto;
	}	

}
