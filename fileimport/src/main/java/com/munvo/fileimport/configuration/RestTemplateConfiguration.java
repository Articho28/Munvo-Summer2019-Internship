package com.munvo.fileimport.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.munvo.fileimport.request.dto.IRequestDto;
import com.munvo.fileimport.request.dto.impl.RequestDto;
import com.munvo.fileimport.request.service.IRequestService;
import com.munvo.fileimport.request.service.impl.RequestService;


/**
 * 
 */
@Configuration
@ComponentScan(basePackages = { "com.munvo.fileimport.request" })
public class RestTemplateConfiguration {
    
    @Bean
    public IRequestDto requestDto() { return new RequestDto(); }
    
    @Bean
    public IRequestService requestService() { return new RequestService(); }
    
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

}
