package com.github.wesleyegberto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BeanToBeInjected {
	
	@Value("${env}")
	private String env;

	public void sayHello() {
		System.out.println("Hello, World from BeanToBeInjected with property: " + env);
	}
}