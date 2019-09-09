package com.munvo.fileimport.request.dto.impl;

import org.springframework.stereotype.Repository;

import com.munvo.fileimport.request.dto.IRequestDto;
import com.munvo.fileimport.request.dto.common.AbstractRequestDto;


/**
 * 
 * @author Artsiom Skliar
 *
 */
@Repository
public class RequestDto extends AbstractRequestDto implements IRequestDto {
	
	public RequestDto() {
		super();
	}

}
