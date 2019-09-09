package com.munvo.fileimport.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {

	private static ApplicationContext restTemplateContext;
	private static SpringContext springContext;
	
	private SpringContext() {
		restTemplateContext = new AnnotationConfigApplicationContext(RestTemplateConfiguration.class);
	}
	
	public static synchronized SpringContext getSpringContext() {
		if (springContext == null) {
			springContext = new SpringContext();
		}
		return springContext;
	}
	
	public ApplicationContext getRestTemplateContext() { return restTemplateContext; }
}
