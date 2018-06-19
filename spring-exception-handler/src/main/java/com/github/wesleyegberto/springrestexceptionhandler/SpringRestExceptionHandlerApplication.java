package com.github.wesleyegberto.springrestexceptionhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringRestExceptionHandlerApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringRestExceptionHandlerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringRestExceptionHandlerApplication.class, args);
	}
}